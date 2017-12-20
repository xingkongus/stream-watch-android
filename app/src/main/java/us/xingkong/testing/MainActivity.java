package us.xingkong.testing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import us.xingkong.live.api.model.App;
import us.xingkong.live.api.model.AppsResult;
import us.xingkong.live.api.model.Result;
import us.xingkong.live.api.network.Client;
import us.xingkong.live.api.network.ResultListener;

public class MainActivity extends AppCompatActivity {

    /**
     * API接口对象
     */
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化接口对象
        client = new Client();

        //登陆
        client.login("hansin", "845612500", new ResultListener<Result>() {
            @Override
            public void onDone(Result result, Exception e) {
                if(result != null)
                    Toast.makeText(MainActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
                if(e != null)
                    e.printStackTrace(System.err);
            }
        });

        //获取直播信息
        client.getApps(new ResultListener<AppsResult>() {
            @Override
            public void onDone(AppsResult result, Exception e) {
                if(result != null){
                    TextView tv = (TextView) MainActivity.this.findViewById(R.id.text);
                    tv.setText(result.getSrc());
                    for(App app : result.getApps()) {
                        tv.setText(tv.getText() +"\n" + app.getAppname() + "\n" + app.getTitle() + app.getMaintext() + "\n\n");
                    }
                }

                if(e != null)
                    e.printStackTrace(System.err);

            }
        });
    }
}
