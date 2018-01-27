package us.xingkong.testing.app.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.Global;
import us.xingkong.testing.R;
import us.xingkong.testing.adapter.ItemAdapter;

/**
 * Created by 饶翰新 on 2017/12/20.
 */

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<App> apps;
    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton fab;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init(boolean bindSuccess) {
        apps = new ArrayList<>();

        findViewById();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.live_list));
        }

        adapter = new ItemAdapter(apps, true, Global.JUMP_TO_LIVE_ACTIVITY);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshdata();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MyAppActivity.class));
            }
        });

        refreshdata();
    }

    private void refreshdata() {
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
                refreshLayout.setRefreshing(false);
            }
        });
    }

    protected void findViewById() {
        recyclerView = findViewById(R.id.appsList);
        refreshLayout = findViewById(R.id.refresh_layout);
        fab = findViewById(R.id.fab);
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.exit_login:
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("username","");
                editor.putString("password","");
                editor.putBoolean("isAutoLogin",false);
                editor.apply();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                Toast.makeText(MainActivity.this, R.string.exit_login_succ, Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case R.id.exit_app:
                android.os.Process.killProcess(android.os.Process.myPid());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
