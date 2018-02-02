package us.xingkong.testing.app.activities.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import us.xingkong.streamsdk.model.Result;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;
import us.xingkong.testing.app.activities.BaseActivity;

/**
 * Created by SeaLynn0 on 2018/1/27.
 */

public class CreateAppActivity extends BaseActivity {

    /*
    ButterKnife
    Eliminate findViewById calls by using @BindView on fields.
     */
    @BindView(R.id.bt_create)
    AppCompatButton create;
    @BindView(R.id.appname)
    AppCompatEditText appanme;
    @BindView(R.id.apptitle)
    AppCompatEditText apptitle;
    @BindView(R.id.maintext)
    AppCompatEditText maintext;

    @Override
    public int getLayout() {
        return R.layout.activity_create_app;
    }

    @Override
    public void init(Bundle savedInstanceState, boolean bindSuccess) {

        /*
        设置ActionBar的属性
         */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.create_app);
        }

//        create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createAppMethod();
//            }
//        });
    }

    @OnClick(R.id.bt_create)
    void createAppMethod() {
        String appname = CreateAppActivity.this.appanme.getText().toString().trim();
        String apptitle = CreateAppActivity.this.apptitle.getText().toString().trim();
        String maintext = CreateAppActivity.this.maintext.getText().toString().trim();

        if (!TextUtils.isEmpty(appname)
                && !TextUtils.isEmpty(apptitle)
                && !TextUtils.isEmpty(maintext)) {
            client.createApp(appname, apptitle, maintext, new ResultListener<Result>() {
                @Override
                public void onDone(Result result, Exception e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else {
                        if (result.getStatus()==100)
                            Toast.makeText(CreateAppActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
                        else if (result.getStatus()==101){
                            Toast.makeText(CreateAppActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else if (result.getStatus()==200){
                            Toast.makeText(CreateAppActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
                            finish();
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
