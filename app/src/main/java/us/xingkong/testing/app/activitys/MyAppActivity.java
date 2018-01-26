package us.xingkong.testing.app.activitys;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.Global;
import us.xingkong.testing.R;
import us.xingkong.testing.adapter.ItemAdapter;

/**
 * Created by SeaLynn0 on 2018/1/18.
 */

public class MyAppActivity extends BaseActivity {

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
        return R.layout.activity_my_app;
    }

    @Override
    public void init(boolean bindSuccess) {
        apps = new ArrayList<>();

        findViewById();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.my_live_list);
        }

        adapter = new ItemAdapter(apps,false, Global.JUMP_TO_STREAM_ACTIVITY);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyAppActivity.this, LinearLayoutManager.VERTICAL, false));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshdata();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAppActivity.this,CreateAppActivity.class));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
