package us.xingkong.testing.app.activities.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import us.xingkong.streamsdk.model.Result;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;
import us.xingkong.testing.app.activities.BaseActivity;

/**
 * Created by SeaLynn0 on 2018/1/28.
 */

public class UpdateAppActivity extends BaseActivity {

    /*
    ButterKnife
    Eliminate findViewById calls by using @BindView on fields.
     */
    @BindView(R.id.bt_update)
    AppCompatButton update;
    @BindView(R.id.apptitle)
    AppCompatEditText apptitle;
    @BindView(R.id.maintext)
    AppCompatEditText maintext;

    String app;

    @Override
    public int getLayout() {
        return R.layout.activity_update_app;
    }

    @Override
    public void init(Bundle savedInstanceState, boolean bindSuccess) {

        app = getIntent().getStringExtra("app");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.update_app);
        }

//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateAppMethod();
//            }
//        });
    }

    @OnClick(R.id.bt_update)
    void updateAppMethod() {
        Log.d("onClick", "updateAppMethod: " + app);
        String apptitle = UpdateAppActivity.this.apptitle.getText().toString();
        String maintext = UpdateAppActivity.this.maintext.getText().toString();

        if (!TextUtils.isEmpty(app)) {
            client.updateApp(app, apptitle, maintext, new ResultListener<Result>() {
                @Override
                public void onDone(Result result, Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        if (result.getStatus() == 200) {
                            Toast.makeText(UpdateAppActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UpdateAppActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
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
