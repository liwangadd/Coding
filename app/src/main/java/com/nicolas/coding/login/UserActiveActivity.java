package com.nicolas.coding.login;

import com.nicolas.coding.R;
import com.nicolas.coding.common.Global;
import com.nicolas.coding.common.umeng.UmengEvent;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_reset_password_base)
public class UserActiveActivity extends ResetPasswordBaseActivity {

    @Override
    String getRequestHost() {
        umengEvent(UmengEvent.USER, "激活账户");
        return Global.HOST_API + "/activate";
    }
}
