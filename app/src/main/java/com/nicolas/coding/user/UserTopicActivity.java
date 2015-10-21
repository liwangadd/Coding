package com.nicolas.coding.user;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;

import com.nicolas.coding.BackActivity;
import com.nicolas.coding.R;
import com.nicolas.coding.common.SaveFragmentPagerAdapter;
import com.nicolas.coding.model.UserObject;
import com.nicolas.coding.subject.SubjectListFragment;
import com.nicolas.coding.subject.SubjectListFragment_;
import com.nicolas.coding.third.WechatTab;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_user_topic)
public class UserTopicActivity extends BackActivity {

    @Extra
    UserObject mUserObject;

    String[] fragmentTitles;

    @ViewById
    WechatTab tabs;

    @ViewById(R.id.pagerFragmentProgram)
    ViewPager pager;

    @AfterViews
    protected final void init() {
        ActionBar actionBar = getSupportActionBar();
        if (mUserObject.isMe()) {
            fragmentTitles = getResources().getStringArray(R.array.user_my_topic_title);
            actionBar.setTitle("我的话题");
        } else {
            fragmentTitles = getResources().getStringArray(R.array.user_other_topic_title);
            actionBar.setTitle("TA的话题");
        }
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);
    }

    class MyPagerAdapter extends SaveFragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles[position];
        }

        @Override
        public int getCount() {
            return fragmentTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            final SubjectListFragment.Type types[] = new SubjectListFragment.Type[]{
                    SubjectListFragment.Type.follow,
                    SubjectListFragment.Type.join
            };

            Fragment fragment = SubjectListFragment_.builder()
                    .userKey(mUserObject.global_key)
                    .mType(types[position])
                    .build();

            saveFragment(fragment);
            return fragment;
        }
    }
}
