package com.nicolas.coding.login;

import com.nicolas.coding.R;
import com.nicolas.coding.common.Global;
import com.nicolas.coding.common.umeng.UmengEvent;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_reset_password_base)
public class ResetPasswordActivity extends ResetPasswordBaseActivity {

    @Override
    String getRequestHost() {
        umengEvent(UmengEvent.USER, "重置密码");
        return Global.HOST_API + "/resetPassword";
    }
}
