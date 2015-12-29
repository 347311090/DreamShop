package com.dreamshop.lhl.dreamshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamshop.lhl.dreamshop.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class RegistActivity extends BaseActivity implements View.OnClickListener{
    private Button bt_duanxin,bt_rg_submit;
    private EditText et_rg_username,et_rg_password,et_duanxinyan;
    private String name;
    private String psd;
    private int type;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg!=null){
                if(msg.arg1==1){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 300;
                        while (i!=0){
                            i-=1;
                            try {
                                Message message = new Message();
                                message.what = 1 ;
                                message.arg2 = i;
                                handler.sendMessage(message);
                                Thread.sleep(1000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if(i==0){
                            Message message = new Message();
                            message.what = 2  ;
                            handler.sendMessage(message);
                        }
                    }
                }).start();
                }else if(msg.what == 1){
                    bt_duanxin.setText(msg.arg2+"秒后重发");
                    bt_duanxin.setClickable(false);
                }else if(msg.what == 2){
                    bt_duanxin.setText("获取短信验证码");
                    bt_duanxin.setClickable(true);
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        Intent it = getIntent();
        if(it!=null){
            type = it.getIntExtra("type",0);
        }
        init(type);
    }

    private void init(int type) {
        bt_rg_submit = (Button) findViewById(R.id.bt_rg_submit);
        et_rg_username = (EditText) findViewById(R.id.et_rg_username);
        et_rg_password = (EditText) findViewById(R.id.et_rg_password);
        et_duanxinyan = (EditText) findViewById(R.id.et_duanxinyan);
        bt_duanxin = (Button) findViewById(R.id.bt_duanxin);
        bt_duanxin.setOnClickListener(this);
        bt_rg_submit.setOnClickListener(this);
        switch (type){
            case 1:
                et_rg_username.setHint("必须输入字母数字组合的账号");
//                .*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]
                bt_duanxin.setVisibility(View.GONE);
                et_duanxinyan.setVisibility(View.GONE);
                break;
            case 2:
                et_rg_username.setHint("请输入您的邮箱");
                bt_duanxin.setVisibility(View.GONE);
                et_duanxinyan.setVisibility(View.GONE);
                break;
            case 3:
                et_rg_username.setHint("请输入您的手机号");
                break;
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_rg_submit){
            name = et_rg_username.getText().toString().trim();
            psd = et_rg_password.getText().toString().trim();
            if(!"".equals(name)&&name!=null&&!"".equals(psd)&&psd!=null){
                final BmobUser user = new BmobUser();
                user.setUsername(name);
                user.setPassword(psd);
                switch (type){
                    case 1:
                        user.signUp(RegistActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(RegistActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                                RegistActivity.this.finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                if(s.startsWith("username")){
                                    Toast.makeText(RegistActivity.this,"注册失败！该用户名已被使用",Toast.LENGTH_SHORT).show();
                                }else if(s.startsWith("The network")){
                                    Toast.makeText(RegistActivity.this,"注册失败！网络异常",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    case 2:
                            user.setUsername(name);
                            user.setEmail(name);
                            user.signUp(RegistActivity.this, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(RegistActivity.this,"注册成功！邮件已发往邮箱",Toast.LENGTH_SHORT).show();
                                    RegistActivity.this.finish();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    if(s.trim().equals("email Must be a valid email address")){
                                        Toast.makeText(RegistActivity.this,"注册失败！请输入正确的邮箱地址",Toast.LENGTH_SHORT).show();
                                    }
                                    else if(s.trim().startsWith("email")){
                                        Toast.makeText(RegistActivity.this,"注册失败！该邮箱已被使用",Toast.LENGTH_SHORT).show();
                                    }else if(s.startsWith("username")){
                                        Toast.makeText(RegistActivity.this,"注册失败！该用户名已被使用",Toast.LENGTH_SHORT).show();
                                    }else if(s.startsWith("The network")){
                                        Toast.makeText(RegistActivity.this,"注册失败！网络异常",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
//                        }
                        break;
                    case 3:
                        final String yzm = et_duanxinyan.getText().toString().trim();
                        if(!"".equals(yzm)&&yzm!=null){
                            user.setMobilePhoneNumber(name);
                            user.signUp(RegistActivity.this, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    BmobSMS.verifySmsCode(RegistActivity.this, name, yzm, new VerifySMSCodeListener() {

                                        @Override
                                        public void done(BmobException ex) {
                                            String id = user.getObjectId();
                                            if (ex == null) {//短信验证码已验证成功
//                                                Log.i("Tag", "验证通过");
                                              Toast.makeText(RegistActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                                RegistActivity.this.finish();
                                            } else {
                                                BmobUser user = new BmobUser();
                                                user.setObjectId(id);
                                                user.delete(RegistActivity.this, new DeleteListener() {
                                                    @Override
                                                    public void onSuccess() {
                                                        Log.i("Tag","success");
                                                    }

                                                    @Override
                                                    public void onFailure(int i, String s) {
                                                            Log.i("Tag",s);
                                                    }
                                                });
//                                                Log.i("Tag", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                                                Toast.makeText(RegistActivity.this, "注册失败！验证码输入有误！", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                @Override
                                public void onFailure(int i, String s) {
                                    if(s.startsWith("username")){
                                        Toast.makeText(RegistActivity.this,"注册失败！该手机已被注册！",Toast.LENGTH_SHORT).show();
                                    }else if(s.startsWith("The network")){
                                        Toast.makeText(RegistActivity.this,"注册失败！网络异常",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(RegistActivity.this,"短信验证码不能为空!",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }else{
                Toast.makeText(RegistActivity.this,"用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
            }

        }else if(v.getId() == R.id.bt_duanxin){
            name = et_rg_username.getText().toString().trim();
            if(!"".equals(name)&&name!=null){
                BmobSMS.requestSMSCode(RegistActivity.this,name , "DreamShop", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if(e==null){//验证码发送成功
                            Log.i("Tag", "短信id：" + integer);//用于查询本次短信发送详情
                            Message message = new Message();
                            message.arg1=1;
                            handler.sendMessage(message);
                        }else{
                            if(e.toString().endsWith("check your network!")){
                                Toast.makeText(RegistActivity.this,"获取验证码失败，请检查网络",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegistActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                            }
                            Log.i("Tag",e.toString());
                        }
                    }
                });
            }
        }
    }
}
