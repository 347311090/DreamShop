package com.dreamshop.lhl.dreamshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private Button bt_bottom_mainpage,bt_bottom_fenlei,bt_bottom_gouwuche;
    private Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {

        bt_bottom_mainpage = (Button) findViewById(R.id.bt_bottom_mainpage);
        bt_bottom_fenlei = (Button) findViewById(R.id.bt_bottom_fenlei);
        bt_bottom_gouwuche = (Button) findViewById(R.id.bt_bottom_gouwuche);
        mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
        bt_infoSelf = (Button) findViewById(R.id.bt_infoSelf);
        bt_myShop = (Button) findViewById(R.id.bt_myShop);
        bt_dingdan = (Button) findViewById(R.id.bt_dingdan);
        bt_zhangHu = (Button) findViewById(R.id.bt_zhangHu);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_regist = (Button) findViewById(R.id.bt_zhuxiao);
        manager = getSupportFragmentManager();
        mFragment = new MainFragment();
        manager.beginTransaction().add(R.id.layout_fragment,mFragment).commit();

        mainFragment = new MainFragment();
        assortmentFragment = new AssortmentFragment();
        shoppingCartFragment = new ShoppingCartFragment();

        bt_bottom_fenlei.setOnClickListener(this);
        bt_bottom_mainpage.setOnClickListener(this);
        bt_bottom_gouwuche.setOnClickListener(this);
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
    public void changeFragment(Fragment from,Fragment to){
        if (to.isHidden()){
            manager.beginTransaction().hide(from).show(to).commit();
        }else {
            manager.beginTransaction().hide(from).add(R.id.layout_fragment,to).commit();
        }
    }
    @Override
    public void onClick(View v) {
        if(mLeftMenu.isOpen==true){
            Intent it = new Intent();
            switch (v.getId()){
                case R.id.bt_infoSelf:
                    Toast.makeText(MainActivity.this,"点击了:"+1,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_myShop:
                    Toast.makeText(MainActivity.this,"点击了:"+2,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_dingdan:
                    Toast.makeText(MainActivity.this,"点击了:"+3,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_zhangHu:
                    Toast.makeText(MainActivity.this,"点击了:"+4,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_login:
                    it.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(it);
                    break;
                case R.id.bt_zhuxiao:
                    Toast.makeText(MainActivity.this,"点击了:"+6,Toast.LENGTH_SHORT).show();
                    break;
            }
        }else{
            switch (v.getId()){
                case R.id.bt_bottom_mainpage:
                    if (!mFragment.equals(mainFragment)) {
                        changeFragment(mFragment,mainFragment);
                        mFragment = mainFragment;
                    }
                    break;
                case R.id.bt_bottom_fenlei:
                    if(!mFragment.equals(assortmentFragment)){
                        changeFragment(mFragment,assortmentFragment);
                        mFragment = assortmentFragment;
                    }
                    break;
                case R.id.bt_bottom_gouwuche:
                    if (!mFragment.equals(shoppingCartFragment)){
                        changeFragment(mFragment,shoppingCartFragment);
                        mFragment = shoppingCartFragment;
                    }
                    break;
            }
        }

    }
}
