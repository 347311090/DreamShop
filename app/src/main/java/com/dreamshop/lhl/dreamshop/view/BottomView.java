package com.dreamshop.lhl.dreamshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.dreamshop.lhl.dreamshop.R;

/**
 * Created by lhl on 2015/11/5.
 */
public class BottomView extends RadioGroup {
    private Context context;
    public BottomView(Context context) {
        super(context);
        setView(context);
    }

    public BottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setView(context);
    }

    private void setView(Context context){
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.bottom,this);
    }
}
