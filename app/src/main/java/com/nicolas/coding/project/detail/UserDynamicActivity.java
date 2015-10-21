package com.nicolas.coding.project.detail;

import android.os.Bundle;

import com.nicolas.coding.BackActivity;
import com.nicolas.coding.R;
import com.nicolas.coding.model.ProjectObject;
import com.nicolas.coding.model.TaskObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

@EActivity(R.layout.activity_user_dynamic)
public class UserDynamicActivity extends BackActivity {

    @Extra
    TaskObject.Members mMember;

    @Extra
    ProjectObject mProjectObject;

    @AfterViews
    protected final void initUserDynamicActivity() {
        getSupportActionBar().setTitle(mMember.user.name);

        ProjectDynamicFragment_ fragment = new ProjectDynamicFragment_();

        Bundle bundle = new Bundle();
        bundle.putInt("mUser_id", mMember.user_id);
        bundle.putSerializable("mProjectObject", mProjectObject);
        bundle.putSerializable("mMember", mMember);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }

}
