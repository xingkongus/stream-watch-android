package us.xingkong.testing.app.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import us.xingkong.streamsdk.model.Result;
import us.xingkong.streamsdk.network.ResultListener;
import us.xingkong.testing.R;
import us.xingkong.testing.app.activities.BaseActivity;

/**
 * Created by LinHai on 2018/1/14.
 */

public class SigninActivity extends BaseActivity {

    /*
    ButterKnife
    Eliminate findViewById calls by using @BindView on fields.
     */
    @BindView(R.id.back)
    AppCompatButton back;
    @BindView(R.id.bt_signin)
    AppCompatButton signin;
    @BindView(R.id.et_username)
    AppCompatEditText username;
    @BindView(R.id.et_nickname)
    AppCompatEditText nickname;
    @BindView(R.id.et_password)
    AppCompatEditText password;

    @Override
    public int getLayout() {
        return R.layout.activity_signin;
    }

    @Override
    public void init(Bundle savedInstanceState, boolean bindSuccess) {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("SignIn onClick", "onClick: ");
                String username = SigninActivity.this.username.getText().toString().trim();
                String nickname = SigninActivity.this.nickname.getText().toString().trim();
                String password = SigninActivity.this.password.getText().toString().trim();

                if (!TextUtils.isEmpty(username)
                        && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(nickname)) {
//                    Log.d("SignIn onClick", "onClick: AllisNotEmpty");
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
}
