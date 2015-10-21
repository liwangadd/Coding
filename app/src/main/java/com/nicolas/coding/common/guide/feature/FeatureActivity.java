package com.nicolas.coding.common.guide.feature;

import com.nicolas.coding.BaseActivity;
import com.nicolas.coding.MainActivity_;
import com.nicolas.coding.R;
import com.nicolas.coding.model.AccountInfo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_feature)
public class FeatureActivity extends BaseActivity {

    @AfterViews
    protected final void initFeatureActivity() {
        AccountInfo.markGuideReaded(this);
    }

    @Click
    void clickGo() {
        finish();
        MainActivity_.intent(this).start();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }
}
