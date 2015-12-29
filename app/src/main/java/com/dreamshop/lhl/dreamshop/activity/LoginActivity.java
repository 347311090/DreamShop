package com.dreamshop.lhl.dreamshop.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamshop.lhl.dreamshop.MainActivity;
import com.dreamshop.lhl.dreamshop.R;
import com.dreamshop.lhl.dreamshop.util.Utils;


import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_log,bt_regist,bt_findpsd;
    private EditText et_log_account,et_log_password;
    private String account;
    private String psd;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg!=null){

                if((boolean)msg.obj){
                    BmobUser user = new BmobUser();
                    user.setUsername(account);
                    user.setPassword(psd);
                    user.login(LoginActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this,"登录失败！您未激活邮箱",Toast.LENGTH_SHORT).show();
                }
            }
        }
    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        bt_log = (Button) findViewById(R.id.bt_log);
        bt_regist = (Button) findViewById(R.id.bt_regist);
        bt_findpsd = (Button) findViewById(R.id.bt_findpsd);
        et_log_account = (EditText) findViewById(R.id.et_log_account);
        et_log_password = (EditText) findViewById(R.id.et_log_password);
        bt_log.setOnClickListener(this);
        bt_regist.setOnClickListener(this);
        bt_findpsd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_log:
                account = et_log_account.getText().toString();
                psd = et_log_password.getText().toString();
                if(!"".equals(account)&&account!=null&&!"".equals(psd)&&psd!=null&&!Utils.isEmail(account)){
                    BmobUser user = new BmobUser();
                    user.setUsername(account);
                    user.setPassword(psd);
                    user.login(LoginActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.i("Tag", s);
                            if(s.toString().endsWith("check your network!")){
                                Toast.makeText(LoginActivity.this,"登录失败，请检查网络",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(!"".equals(account)&&account!=null&&!"".equals(psd)&&psd!=null&&Utils.isEmail(account)){
                    BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
                    query.addWhereEqualTo("username",account);
                    query.findObjects(LoginActivity.this, new FindListener<BmobUser>() {
                        @Override
                        public void onSuccess(List<BmobUser> list) {
                            BmobUser bmobUser = list.get(0);
                            Message message = new Message();
                            message.obj = bmobUser.getEmailVerified();
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(int i, String s) {
                           Toast.makeText(LoginActivity.this,"该账户并没被注册",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.bt_regist:
                Intent it = new Intent(LoginActivity.this,RegistTypeAcitivty.class);
                startActivity(it);
                break;
            case R.id.bt_findpsd:
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("找回密码");
                builder.setMessage("请输入要找回密码的邮箱");
                final EditText text = new EditText(LoginActivity.this);
                builder.setView(text);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = text.getText().toString().trim();
                        if (!email.equals("") && email != null && Utils.isEmail(email)) {
                            BmobUser.resetPasswordByEmail(LoginActivity.this, email, new ResetPasswordByEmailListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(LoginActivity.this,"修改密码的邮件已发到到指定邮箱！",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(LoginActivity.this,"发生失败！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;
        }
    }
}
