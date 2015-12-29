package com.dreamshop.lhl.dreamshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dreamshop.lhl.dreamshop.activity.BaseActivity;
import com.dreamshop.lhl.dreamshop.activity.LoginActivity;
import com.dreamshop.lhl.dreamshop.fragment.AssortmentFragment;
import com.dreamshop.lhl.dreamshop.fragment.MainFragment;
import com.dreamshop.lhl.dreamshop.fragment.ShoppingCartFragment;
import com.dreamshop.lhl.dreamshop.view.SlidingMenu;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private SlidingMenu mLeftMenu ;
    private Button bt_infoSelf,bt_myShop,bt_dingdan,bt_zhangHu,bt_login,bt_regist;
    private LinearLayout layout_fragment;
    private FragmentManager manager;
    private AssortmentFragment assortmentFragment;
    private MainFragment mainFragment;
    private ShoppingCartFragment shoppingCartFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
        bt_infoSelf = (Button) findViewById(R.id.bt_infoSelf);
        bt_myShop = (Button) findViewById(R.id.bt_myShop);
        bt_dingdan = (Button) findViewById(R.id.bt_dingdan);
        bt_zhangHu = (Button) findViewById(R.id.bt_zhangHu);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_regist = (Button) findViewById(R.id.bt_zhuxiao);
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.layout_fragment,new MainFragment()).commit();

        bt_infoSelf.setOnClickListener(this);
        bt_myShop.setOnClickListener(this);
        bt_dingdan.setOnClickListener(this);
        bt_zhangHu.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_regist.setOnClickListener(this);
    }
    public void toggleMenu(View view)
    {
        mLeftMenu.toggle();
    }


    @Override
    public void onClick(View v) {
        if(mLeftMenu.isOpen==true){
            Intent it = new Intent();
            switch (v.getId()){
                case R.id.bt_infoSelf:
                    Toast.makeText(MainActivity.this,"点击了:"+1,0).show();
                    break;
                case R.id.bt_myShop:
                    Toast.makeText(MainActivity.this,"点击了:"+2,0).show();
                    break;
                case R.id.bt_dingdan:
                    Toast.makeText(MainActivity.this,"点击了:"+3,0).show();
                    break;
                case R.id.bt_zhangHu:
                    Toast.makeText(MainActivity.this,"点击了:"+4,0).show();
                    break;
                case R.id.bt_login:
                    it.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(it);
                    break;
                case R.id.bt_zhuxiao:
                    Toast.makeText(MainActivity.this,"点击了:"+6,0).show();
                    break;
            }
        }

    }
}
