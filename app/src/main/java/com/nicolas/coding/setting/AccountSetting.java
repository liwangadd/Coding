package com.nicolas.coding.setting;

import android.widget.TextView;

import com.nicolas.coding.BackActivity;
import com.nicolas.coding.MyApp;
import com.nicolas.coding.R;
import com.nicolas.coding.model.UserObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_account_setting)
public class AccountSetting extends BackActivity {

    @ViewById
    TextView email;

    @ViewById
    TextView suffix;

    @AfterViews
    final void initAccountSetting() {
        UserObject userObject = MyApp.sUserObject;
        email.setText(userObject.email);
        suffix.setText(userObject.global_key);
    }

    @Click
    void passwordSetting() {
        SetPasswordActivity_.intent(this).start();
    }
}
