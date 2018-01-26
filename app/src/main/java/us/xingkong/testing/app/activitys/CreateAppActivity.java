package us.xingkong.testing.app.activitys;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import us.xingkong.streamsdk.model.Result;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;

/**
 * Created by SeaLynn0 on 2018/1/27.
 */

public class CreateAppActivity extends BaseActivity {

//    private Button back;
    private Button create;
    private EditText appanme;
    private EditText apptitle;
    private EditText maintext;

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_create_app;
    }

    @Override
    public void init(boolean bindSuccess) {
        findViewById();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.create_app);
        }

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAppMethod();
            }
        });
    }

    private void createAppMethod() {
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

    private void findViewById() {
//        back = findViewById(R.id.back);
        create = findViewById(R.id.Button);
        appanme = findViewById(R.id.appname);
        apptitle = findViewById(R.id.apptitle);
        maintext = findViewById(R.id.maintext);
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
