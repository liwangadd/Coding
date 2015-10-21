package com.nicolas.coding.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.nicolas.coding.BaseActivity;
import com.nicolas.coding.R;
import com.nicolas.coding.maopao.MaopaoListFragment;
import com.nicolas.coding.maopao.MaopaoListFragment_;

public class UserMaopaoActivity extends BaseActivity {

    public static final String PARAM_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_maopao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int userId = getIntent().getIntExtra(PARAM_ID, 0);

        Fragment fragment = MaopaoListFragment_.builder().mType(MaopaoListFragment.Type.user).userId(userId).build();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, "所有冒泡")
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
