package us.xingkong.testing.app.fragments.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import us.xingkong.streamsdk.model.App;
import us.xingkong.streamsdk.model.AppsResult;
import us.xingkong.streamsdk.network.Client;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;
import us.xingkong.testing.adapter.ItemAdapter;
import us.xingkong.testing.app.activities.activities.StreamActivity;
import us.xingkong.testing.app.activities.activities.UpdateAppActivity;
import us.xingkong.testing.app.fragments.BaseFragment;
import us.xingkong.testing.util.Global;

/**
 * Created by SeaLynn0 on 2018/1/30.
 */

public class MyAppsFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

    View v;

    @BindView(R.id.my_apps_list)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private ItemAdapter adapter;
    private List<App> apps;

    Client client = new Client(getInstantContext());

    /**
     * 直播权限获取的唯一标识码
     */
    public static final int PERMISSIONS_CAMERA_AND_RECORD = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_my_apps, container, false);
        ButterKnife.bind(this, v);

        apps = new ArrayList<>();

        adapter = new ItemAdapter(apps, false);
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, App app) {
                if (EasyPermissions.hasPermissions(getInstantContext(), Global.PERMS)) {
                    Intent intent = new Intent(getInstantContext(), StreamActivity.class);
                    intent.putExtra("token", app.getToken());
                    startActivity(intent);
                } else {
                    EasyPermissions.requestPermissions(MyAppsFragment.this, "直播需要读写相机和录音权限", PERMISSIONS_CAMERA_AND_RECORD, Global.PERMS);
                }

            }
        });
        adapter.setOnItemLongClickListener(new ItemAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, App app) {
                Intent intent = new Intent(getInstantContext(), UpdateAppActivity.class);
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
        client.getUserApps(new ResultListener<AppsResult>() {
            @Override
            public void onDone(AppsResult result, Exception e) {
                if (result != null) {
                    apps.clear();
                    apps.addAll(result.getApps());
                    adapter.notifyDataSetChanged();
                }

                if (e != null)
                    e.printStackTrace(System.err);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d("MyAppsFragment", "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
