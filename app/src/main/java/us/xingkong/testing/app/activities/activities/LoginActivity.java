package us.xingkong.testing.app.activities.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import us.xingkong.streamsdk.model.Result;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;
import us.xingkong.testing.app.activities.BaseActivity;

public class LoginActivity extends BaseActivity {

    /*
    ButterKnife
    Eliminate findViewById calls by using @BindView on fields.
     */
    @BindView(R.id.bt_signin)
    AppCompatButton signin;
    @BindView(R.id.bt_login)
    AppCompatButton login;
    @BindView(R.id.et_username)
    AppCompatEditText username;
    @BindView(R.id.et_password)
    AppCompatEditText usercode;

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void init(Bundle savedInstanceState, boolean bindSuccess) {

        /*
        获取SharedPreferences来中的用户信息来登陆
         */
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        if (pref.getBoolean("isAutoLogin", false)) {
            loginMethod(pref.getString("username", null), pref.getString("password", null));
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SigninActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String userPass = usercode.getText().toString();
                loginMethod(userName,userPass);
            }
        });
    }

    /**
     * 将登陆封装成一个方法
     *
     * @param userName
     * @param userPass
     */
    private void loginMethod(final String userName, final String userPass) {
        username.setEnabled(false);
        usercode.setEnabled(false);
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPass)) {
            Toast.makeText(LoginActivity.this, R.string.name_code_empty, Toast.LENGTH_SHORT).show();
        } else {
            client.login(userName, userPass, new ResultListener<Result>() {
                @Override
                public void onDone(Result result, Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        if (result.getStatus() == 100) {    //登陸失败
                            Toast.makeText(LoginActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                        } else if (result.getStatus() == 200) { //登录成功
                            //使用SharedPreferences来存储用户登陆信息
                            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                            editor.putString("username",userName);
                            editor.putString("password",userPass);
                            editor.putBoolean("isAutoLogin",true);
                            editor.apply();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            Toast.makeText(LoginActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                            username.setEnabled(true);
                            usercode.setEnabled(true);
                            finish();
                        }
                    }
                }
            });
        }
    }

}


