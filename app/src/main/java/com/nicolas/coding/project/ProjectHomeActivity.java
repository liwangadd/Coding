package com.nicolas.coding.project;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.nicolas.coding.BaseActivity;
import com.nicolas.coding.FileUrlActivity;
import com.nicolas.coding.R;
import com.nicolas.coding.model.ProjectObject;
import com.nicolas.coding.project.detail.ProjectActivity;
import com.nicolas.coding.project.init.InitProUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;
import org.json.JSONException;
import org.json.JSONObject;

@EActivity(R.layout.activity_project_home)
//@OptionsMenu(R.menu.menu_project_home)
public class ProjectHomeActivity extends BaseActivity {

    @Extra
    ProjectObject mProjectObject;

    @Extra
    ProjectActivity.ProjectJumpParam mJumpParam;

    @Extra
    boolean mNeedUpdateList = false; // 需要更新项目列表的排序

    @ViewById
    FrameLayout container;

    @StringArrayRes
    String[] dynamic_type_params;

    String mProjectUrl;

    private Fragment mCurrentFragment;

    @AfterViews
    protected void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mProjectObject != null) {
            initFragment();
        } else if (mJumpParam != null) {
            mProjectUrl = String.format(FileUrlActivity.HOST_PROJECT, mJumpParam.mUser, mJumpParam.mProject);
            getNetwork(mProjectUrl, mProjectUrl);
        } else {
            finish();
        }
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {
        if (tag.equals(mProjectUrl)) {
            if (code == 0) {
                mProjectObject = new ProjectObject(respanse.getJSONObject("data"));
                initFragment();
            } else {
                showErrorMsg(code, respanse);
            }
        } else if (tag.equals(PrivateProjectHomeFragment.HOST_VISTIT)) {
            if (code == 0) {
                sendBroadcast(new Intent(ProjectFragment.RECEIVER_INTENT_REFRESH_PROJECT));
            } else {
                showErrorMsg(code, respanse);
            }
        }
    }

    private void initFragment() {
        if (mNeedUpdateList) {
            String url = String.format(PrivateProjectHomeFragment.HOST_VISTIT, mProjectObject.getId());
            getNetwork(url, PrivateProjectHomeFragment.HOST_VISTIT);
        }

        Fragment fragment;
        if (mProjectObject.isPublic()) {
            fragment = PublicProjectHomeFragment_.builder()
                    .mProjectObject(mProjectObject)
                    .build();
        } else {
            fragment = PrivateProjectHomeFragment_.builder()
                    .mProjectObject(mProjectObject)
                    .build();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .commit();
        mCurrentFragment = fragment;
    }

    @OptionsItem(android.R.id.home)
    final protected void clickBack() {
        if (mCurrentFragment instanceof BaseProjectHomeFragment) {
            if (((BaseProjectHomeFragment) mCurrentFragment).isBackToRefresh()) {
                InitProUtils.backIntentToMain(this);
                return;
            }
        }

        finish();
    }
}
