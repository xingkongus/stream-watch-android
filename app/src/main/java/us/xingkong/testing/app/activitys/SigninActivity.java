package us.xingkong.testing.app.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import us.xingkong.streamsdk.model.Result;
import us.xingkong.streamsdk.network.Client;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;

/**
 * Created by LinHai on 2018/1/14.
 */

public class SigninActivity extends AppCompatActivity {

    private Button back;
    private Button signin;
    private EditText username;
    private EditText nickname;
    private EditText password;

    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initViews();
        client = new Client();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this, LoginActivity.class));
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SignIn onClick", "onClick: ");
                String username = SigninActivity.this.username.getText().toString();
                String nickname = SigninActivity.this.nickname.getText().toString();
                String password = SigninActivity.this.password.getText().toString();

                if (!TextUtils.isEmpty(username)
                        && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(nickname)) {
                    Log.d("SignIn onClick", "onClick: AllisNotEmpty");
                    client.signin(username, password, nickname, new ResultListener<Result>() {
                        @Override
                        public void onDone(Result result, Exception e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else {
                                if (result.getStatus()==100)
                                    Toast.makeText(SigninActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
                                else if (result.getStatus()==200){
                                    Toast.makeText(SigninActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void initViews() {
        back = findViewById(R.id.back);
        signin = findViewById(R.id.Button);
        username = findViewById(R.id.regist_user);
        nickname = findViewById(R.id.nickname);
        password = findViewById(R.id.password);
    }
}
