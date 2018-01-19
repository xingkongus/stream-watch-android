package us.xingkong.testing.app.activitys;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;
import us.xingkong.testing.adapter.ItemAdapter;

/**
 * Created by SeaLynn0 on 2018/1/18.
 */

public class MyLiveActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<App> apps;
    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton fab;

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_mylive;
    }

    @Override
    public void init(boolean bindSuccess) {
        apps = new ArrayList<>();

        findViewById();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.live_list);
        }

        adapter = new ItemAdapter(apps,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyLiveActivity.this, LinearLayoutManager.VERTICAL, false));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshdata();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyLiveActivity.this,StreamActivity.class));
            }
        });

        refreshdata();
    }

    private void refreshdata() {
        client.getUserApps(new ResultListener<AppsResult>() {
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
                refreshLayout.setRefreshing(false);
            }
        });
    }

    protected void findViewById() {
        recyclerView = findViewById(R.id.my_apps_list);
        refreshLayout = findViewById(R.id.refresh_layout);
        fab = findViewById(R.id.fab);
    }
}
