package com.nicolas.coding.subject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nicolas.coding.BackActivity;
import com.nicolas.coding.BaseActivity;
import com.nicolas.coding.MyApp;
import com.nicolas.coding.R;
import com.nicolas.coding.common.Global;
import com.nicolas.coding.common.SaveFragmentPagerAdapter;
import com.nicolas.coding.model.Subject;
import com.nicolas.coding.subject.loop.AutoScrollLoopViewPager;
import com.nicolas.coding.third.WechatTab;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 15-7-20.
 * 话题墙
 */
@EActivity(R.layout.activity_subject_detail)
public class SubjectUsersActivity extends BackActivity {

    @Extra
    int topicId;


    @AfterViews
    protected final void initSubjectUserActivity() {

        setTitle("全部参与者");
        if (topicId > 0) {
            showSubjectDetailFragment();
        }
    }


    private void showSubjectDetailFragment() {
        Fragment fragment = SubjectUserFragment_.builder()
                .topicId(topicId)
                .build();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
