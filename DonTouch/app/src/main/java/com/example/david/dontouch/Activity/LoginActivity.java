package com.example.david.dontouch.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.david.dontouch.Dao.SQLiteDB;
import com.example.david.dontouch.Model.User;
import com.example.david.dontouch.R;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText username, password;
    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button forgive_pwd;
    private Button bt_pwd_eye;
    private Button login;
    private Button register;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {

        username = (EditText) findViewById(R.id.username);
        // 监听文本框内容变化
        username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 获得文本框中的用户
                String user = username.getText().toString().trim();
                if ("".equals(user)) {
                    // 用户名为空,设置按钮不可见
                    bt_username_clear.setVisibility(View.INVISIBLE);
                } else {
                    // 用户名不为空，设置按钮可见
                    bt_username_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });
        password = (EditText) findViewById(R.id.password);
        // 监听文本框内容变化
        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 获得文本框中的用户
                String pwd = password.getText().toString().trim();
                if ("".equals(pwd)) {
                    // 用户名为空,设置按钮不可见
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                } else {
                    // 用户名不为空，设置按钮可见
                    bt_pwd_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
        bt_username_clear.setOnClickListener(this);

        bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
        bt_pwd_clear.setOnClickListener(this);

        bt_pwd_eye = (Button) findViewById(R.id.bt_pwd_eye);
        bt_pwd_eye.setOnClickListener(this);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        forgive_pwd = (Button) findViewById(R.id.forgive_pwd);
        forgive_pwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_username_clear:
                // 清除登录名
                username.setText("");
                break;
            case R.id.bt_pwd_clear:
                // 清除密码
                password.setText("");
                break;
            case R.id.bt_pwd_eye:
                // 密码可见与不可见的切换
                if (isOpen) {
                    isOpen = false;
                } else {
                    isOpen = true;
                }

                // 默认isOpen是false,密码不可见
                changePwdOpenOrClose(isOpen);

                break;
            case R.id.login:
                // TODO 登录按钮
                String name_l = username.getText().toString().trim();
                String pass_l = password.getText().toString().trim();
                int result_l = SQLiteDB.getInstance(getApplicationContext()).Quer(pass_l, name_l);
                if (result_l == 1)
                {
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (result_l == 0){
                    Toast.makeText(LoginActivity.this, "用户名不存在！", Toast.LENGTH_SHORT).show();

                }
                else if(result_l == -1)
                {
                    Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.register:
                // 注册按钮
                String name_r = username.getText().toString().trim();
                String pass_r = password.getText().toString().trim();

                User user=new User();
                user.setUsername(name_r);
                user.setUserpwd(pass_r);

                int result_r = SQLiteDB.getInstance(getApplicationContext()).saveUser(user);
                if (result_r == 1){
                    Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }else  if (result_r == -1) {
                    Toast.makeText(LoginActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.forgive_pwd:
                // 忘记密码按钮
                Toast.makeText(LoginActivity.this, "忘记密码", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    /**
     * 密码可见与不可见的切换
     *
     * @param flag
     */
    private void changePwdOpenOrClose(boolean flag) {
        // 第一次过来是false，密码不可见
        if (flag) {
            // 密码可见
            bt_pwd_eye.setBackgroundResource(R.drawable.password_open);
            // 设置EditText的密码可见
            password.setTransformationMethod(HideReturnsTransformationMethod
                    .getInstance());
        } else {
            // 密码不接见
            bt_pwd_eye.setBackgroundResource(R.drawable.password_close);
            // 设置EditText的密码隐藏
            password.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
        }
    }
}
