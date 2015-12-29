package com.dreamshop.lhl.dreamshop.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamshop.lhl.dreamshop.R;

/**
 * Created by lhl on 2015/11/4.
 */
public class ShoppingCartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppingcart,null);
        return view;
    }
}
