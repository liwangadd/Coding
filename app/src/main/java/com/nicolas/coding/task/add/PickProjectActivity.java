package com.nicolas.coding.task.add;

import android.support.v4.app.Fragment;

import com.nicolas.coding.BackActivity;
import com.nicolas.coding.R;
import com.nicolas.coding.project.ProjectFragment;
import com.nicolas.coding.project.ProjectFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_pick_project)
public class PickProjectActivity extends BackActivity {

    @AfterViews
    protected final void initPickProjectActivity() {
//        Fragment fragment = UserProjectListFragment_.builder()
//                .mUserObject(MyApp.sUserObject)
//                .mType(UserProjectListFragment.Type.all_private)
//                .mPickProject(true)
//                .build();
        Fragment fragment = ProjectFragment_.builder()
                .type(ProjectFragment.Type.Pick)
                .build();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
