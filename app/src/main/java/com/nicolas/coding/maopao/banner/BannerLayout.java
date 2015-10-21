package com.nicolas.coding.maopao.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.nicolas.coding.R;

/**
 * Created by chenchao on 15/7/30.
 * 广告条控件
 */
public class BannerLayout extends FrameLayout {

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        View v = LayoutInflater.from(context).inflate(R.layout.maopao_banner, null);
        addView(v);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
//        BannerAdapter adapter = new BannerAdapter()
    }


}
