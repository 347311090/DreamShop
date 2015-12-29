package com.dreamshop.lhl.dreamshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dreamshop.lhl.dreamshop.R;

public class RegistTypeAcitivty extends BaseActivity {
    private Button bt_rg_putong,bt_rg_email,bt_rg_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_type_acitivty);
        init();
    }

    private void init() {
        bt_rg_putong = (Button) findViewById(R.id.bt_rg_putong);
        bt_rg_email = (Button) findViewById(R.id.bt_rg_email);
        bt_rg_phone = (Button) findViewById(R.id.bt_rg_phone);
    }
    public void clickItem(View v){
        Intent it = new Intent();
        it.setClass(RegistTypeAcitivty.this,RegistActivity.class);
        switch (v.getId()){
            case R.id.bt_rg_putong:
                it.putExtra("type",1);
                break;
            case R.id.bt_rg_email:
                it.putExtra("type",2);
                break;
            case R.id.bt_rg_phone:
                it.putExtra("type",3);
                break;
        }
        startActivity(it);
    }
}
