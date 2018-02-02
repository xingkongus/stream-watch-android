package us.xingkong.testing.app.fragments.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.Client;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;
import us.xingkong.testing.adapter.ItemAdapter;
import us.xingkong.testing.app.activities.activities.LiveActivity;
import us.xingkong.testing.app.fragments.BaseFragment;

/**
 * Created by SeaLynn0 on 2018/1/30.
 */

public class LivingListFragment extends BaseFragment {

    View v;

    @BindView(R.id.apps_list)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private ItemAdapter adapter;
    private List<App> apps;

    Client client = new Client(getInstantContext());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_living_list,container,false);
        ButterKnife.bind(this,v);
        apps = new ArrayList<>();

        adapter = new ItemAdapter(apps, true);
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, App app) {
                Intent intent = new Intent(getInstantContext(), LiveActivity.class);
                intent.putExtra("app", app.getAppname());
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getInstantContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshdata();
            }
        });

        refreshdata();

        return v;
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

}
