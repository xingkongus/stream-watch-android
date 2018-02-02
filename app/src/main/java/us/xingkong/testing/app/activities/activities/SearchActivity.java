package us.xingkong.testing.app.activities.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.OnClick;
import us.xingkong.streamsdk.model.GetAppResult;
import us.xingkong.streamsdk.model.StatusResult;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;
import us.xingkong.testing.app.activities.BaseActivity;

/**
 * Created by SeaLynn0 on 2018/1/31.
 */

public class SearchActivity extends BaseActivity {

    @BindView(R.id.username)
    AppCompatEditText searchInfo;
    @BindView(R.id.bt_search)
    AppCompatButton search;
    @BindView(R.id.search_app)
    AppCompatRadioButton searchApp;
    @BindView(R.id.search_user)
    AppCompatRadioButton searchUser;
    @BindView(R.id.search_result)
    AppCompatTextView et_result;

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void init(Bundle savedInstanceState, boolean bindSuccess) {
        /*
        设置ActionBar的属性
         */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.search);
        }
    }

    @OnClick(R.id.bt_search)
    public void onClickToSearch() {
        String info = searchInfo.getText().toString();
        if (!TextUtils.isEmpty(info))
            if (searchApp.isChecked()) {
                searchAppMethod(info);
            } else if (searchUser.isChecked()) {
                searchUserMethod(info);
            }
    }

    private void searchUserMethod(String info) {
        client.getUser(info, new ResultListener<StatusResult>() {
            @Override
            public void onDone(StatusResult result, Exception e) {
                Log.d("result", "onDone: "+result.getResult());
                if (e != null)
                    e.printStackTrace();
                else et_result.setText(result.getResult());
            }
        });
    }

    private void searchAppMethod(String info) {
        client.getApp(info, new ResultListener<GetAppResult>() {
            @Override
            public void onDone(GetAppResult result, Exception e) {
                Log.d("result", "onDone: "+result.getResult());
                if (e != null)
                    e.printStackTrace();
                else et_result.setText(result.getResult());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
