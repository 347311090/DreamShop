package com.dreamshop.lhl.dreamshop.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dreamshop.lhl.dreamshop.R;

import cn.bmob.v3.Bmob;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(getApplication(),"edc5614e8b53f0981c4c1195ae76182b");
    }

}
