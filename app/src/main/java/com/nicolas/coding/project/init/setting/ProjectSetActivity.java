package com.nicolas.coding.project.init.setting;

import android.os.Bundle;

import com.nicolas.coding.BackActivity;
import com.nicolas.coding.R;
import com.nicolas.coding.model.ProjectObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by jack wang on 2015/3/31.
 */
@EActivity(R.layout.init_activity_common)
public class ProjectSetActivity extends BackActivity {

    ProjectSetFragment fragment;

    @AfterViews
    protected final void initProjectSetActivity() {
        fragment = ProjectSetFragment_.builder().build();
        ProjectObject projectObject = (ProjectObject) getIntent().getSerializableExtra("projectObject");
        Bundle bundle = new Bundle();
        bundle.putSerializable("projectObject", projectObject);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (fragment.isBackToRefresh) {
            fragment.backToRefresh();
            return;
        }
        finish();
    }
}
