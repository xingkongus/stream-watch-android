package us.xingkong.testing.app.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;

/**
 * Created by 饶翰新 on 2017/12/20.
 */

public class MainActivity extends BaseActivity {

    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected List<App> apps;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init(boolean bindSuccess) {
        apps = new ArrayList<>();

        findViewById();
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

        adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.appitem, parent, false);
                return new AppHolder(v);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                AppHolder h = (AppHolder)holder;
                final App app = apps.get(position);
                System.out.print(app.getAppname());
                h.appTitle.setText(app.getTitle());
                h.appUser.setText(app.getUser());
                h.appMaintext.setText(app.getMaintext());

                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,LiveActivity.class);
                        intent.putExtra("app",app.getAppname());
                        startActivity(intent);
                    }
                };

                h.appImg.setOnClickListener(onClickListener);
                h.appTitle.setOnClickListener(onClickListener);
                h.appUser.setOnClickListener(onClickListener);
                h.appMaintext.setOnClickListener(onClickListener);
            }

            @Override
            public int getItemCount() {
                return apps.size();
            }

            class AppHolder extends RecyclerView.ViewHolder{

                public ImageView appImg;
                public TextView appTitle;
                public TextView appUser;
                public TextView appMaintext;

                public AppHolder(View itemView) {
                    super(itemView);

                    appImg = itemView.findViewById(R.id.app_img);
                    appTitle = itemView.findViewById(R.id.app_title);
                    appUser = itemView.findViewById(R.id.app_user);
                    appMaintext = itemView.findViewById(R.id.app_maintext);
                }
            }

        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
    }
}
