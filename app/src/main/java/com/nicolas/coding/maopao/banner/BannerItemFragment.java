package com.nicolas.coding.maopao.banner;

import android.view.View;
import android.widget.ImageView;

import com.nicolas.coding.WebActivity_;
import com.umeng.analytics.MobclickAgent;

import com.nicolas.coding.R;
import com.nicolas.coding.common.network.BaseFragment;
import com.nicolas.coding.common.umeng.UmengEvent;
import com.nicolas.coding.model.BannerObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_banner_item)
public class BannerItemFragment extends BaseFragment {

    @ViewById
    ImageView image;
    @ViewById
    View itemLayout;

    @FragmentArg
    BannerObject data;

    @AfterViews
    protected final void initBannerItemFragment() {
        updateDisplay();
    }

    public void setData(BannerObject bannerObject) {
        data = bannerObject;
        updateDisplay();
    }

    private void updateDisplay() {
        iconfromNetwork(image, data.getImage());
    }

    @Click
    protected void itemLayout() {
        WebActivity_.intent(getActivity())
                .url(data.getLink())
                .start();
        MobclickAgent.onEvent(getActivity(), UmengEvent.MAOPAO, "点击banner");
    }
}
