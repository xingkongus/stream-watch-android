package us.xingkong.testing.app.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import us.xingkong.streamsdk.model.Result;
import us.xingkong.streamsdk.model.StatusResult;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;

public class LoginActivity extends BaseActivity {

    private Button signin;
    private Button login;
    private EditText username;
    private EditText usercode;

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void init(boolean bindSuccess) {
        findViewById();

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        if (pref.getBoolean("isAutoLogin", false)) {
            loginMethod(pref.getString("username", null), pref.getString("password", null));
        }
//        client.checkLoginStatus(new ResultListener<StatusResult>() {
//            @Override
//            public void onDone(StatusResult result, Exception e) {
//                Log.d("checkLoginStatus", "onDone: "+ result.getStatus());
//                startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                Toast.makeText(LoginActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SigninActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = username.getText().toString();
                final String userPass = usercode.getText().toString();
                loginMethod(userName,userPass);
            }
        });
    }

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
                            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                            editor.putString("username",userName);
                            editor.putString("password",userPass);
                            editor.putBoolean("isAutoLogin",true);
                            editor.apply();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            Toast.makeText(LoginActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            });
        }
        username.setEnabled(true);
        usercode.setEnabled(true);
    }

    private void findViewById() {
        signin = findViewById(R.id.bt_signin);
        login = findViewById(R.id.bt_login);
        username = findViewById(R.id.et_username);
        usercode = findViewById(R.id.et_usercode);
    }

}


