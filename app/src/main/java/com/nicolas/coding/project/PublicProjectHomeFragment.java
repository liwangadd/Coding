package com.nicolas.coding.project;


import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.nicolas.coding.project.detail.ProjectActivity_;
import com.readystatesoftware.viewbadger.BadgeView;

import com.nicolas.coding.R;
import com.nicolas.coding.common.Global;
import com.nicolas.coding.common.RedPointTip;
import com.nicolas.coding.common.umeng.UmengEvent;
import com.nicolas.coding.model.DynamicObject;
import com.nicolas.coding.model.ProjectObject;
import com.nicolas.coding.project.detail.ProjectActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

@EFragment(R.layout.fragment_public_project_home)
public class PublicProjectHomeFragment extends BaseProjectHomeFragment {

    @ViewById
    View buttonStar, buttonWatch, buttonFork;

    ProjectMarkButton mButtonStar;
    ProjectMarkButton mButtonWatch;
    ProjectMarkButton mButtonFork;
    String mUrlStar;
    String mUrlUnstar;
    String mUrlWatch;
    String mUrlUnwatch;
    private String httpProjectObject;
    private String forkUrl;

    @AfterViews
    final void init() {
        mUrlStar = mProjectObject.getHttpStar(true);
        mUrlUnstar = mProjectObject.getHttpStar(false);
        mUrlWatch = mProjectObject.getHttpWatch(true);
        mUrlUnwatch = mProjectObject.getHttpWatch(false);

        View mRoot = getView();
        initHead2();
        initHead3(mRoot);

        httpProjectObject = mProjectObject.getHttpProjectObject();
        getNetwork(httpProjectObject);
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {
        if (tag.equals(httpProjectObject)) {
            if (code == 0) {
                mProjectObject = new ProjectObject(respanse.getJSONObject("data"));
                initHead2();
            } else {
                showErrorMsg(code, respanse);
            }
        } else if (tag.equals(mUrlStar) || tag.equals(mUrlUnstar)) {
            umengEvent(UmengEvent.PROJECT, "收藏项目");
            if (tag.equals(mUrlStar)) {
                umengEvent(UmengEvent.CODE, "收藏");
            } else {
                umengEvent(UmengEvent.CODE, "取消收藏");
            }
            if (code != 0) {
                mButtonStar.changeState();
                showErrorMsg(code, respanse);
            }
        } else if (tag.equals(mUrlWatch) || tag.equals(mUrlUnwatch)) {
            umengEvent(UmengEvent.PROJECT, "关注项目");

            if (tag.equals(mUrlWatch)) {
                umengEvent(UmengEvent.CODE, "关注");
            } else {
                umengEvent(UmengEvent.CODE, "取消关注");
            }
            if (code != 0) {
                mButtonWatch.changeState();
                showErrorMsg(code, respanse);
            }
        } else if (tag.equals(forkUrl)) {
            showProgressBar(false);
            if (code == 0) {
                umengEvent(UmengEvent.PROJECT, "Fork");

                umengEvent(UmengEvent.CODE, "Fork");
                JSONObject jsonData = respanse.getJSONObject("data");
                String projectName = jsonData.optString("name");
                DynamicObject.Owner owner = new DynamicObject.Owner(jsonData.optJSONObject("owner"));
                ProjectActivity.ProjectJumpParam param = new ProjectActivity.ProjectJumpParam(owner.global_key,
                        projectName);
                ProjectHomeActivity_
                        .intent(this)
                        .mJumpParam(param)
                        .start();
                mButtonFork.changeState();
            } else {
                showErrorMsg(code, respanse);
            }
        }
    }

    private void initHead2() {
        String[][] titles = new String[][]{
                new String[]{"收藏", "已收藏"},
                new String[]{"关注", "已关注"},
                new String[]{"Fork", "Fork"},
        };

        int[][] titlesColors = new int[][]{
                new int[]{0xff222222, 0xff3bbd79},
                new int[]{0xff222222, 0xff3bbd79},
                new int[]{0xff222222, 0xff666666},
        };

        int[][] icons = new int[][]{
                new int[]{R.drawable.project_home_button_public_star, R.drawable.project_home_button_public_ok},
                new int[]{R.drawable.project_home_button_public_watch, R.drawable.project_home_button_public_ok},
                new int[]{R.drawable.project_home_button_public_fork, R.drawable.project_home_button_public_fork},
        };

        mButtonStar = new ProjectMarkButton(buttonStar, titles[0], titlesColors[0], icons[0], mProjectObject.stared, mProjectObject.star_count);
        mButtonWatch = new ProjectMarkButton(buttonWatch, titles[1], titlesColors[1], icons[1], mProjectObject.watched, mProjectObject.watch_count);
        mButtonFork = new ProjectMarkButton(buttonFork, titles[2], titlesColors[2], icons[2], mProjectObject.forked, mProjectObject.fork_count);
    }

    private void initHead3(View root) {
        final int[] buttons = new int[]{
                R.id.itemDynamic,
                R.id.itemTopic,
                R.id.itemCode,
                R.id.itemMember,
                R.id.itemReadme,
                R.id.itemMerge
        };

        final int[] buttonBackgrounds = new int[]{
                R.drawable.project_button_icon_dynamic,
                R.drawable.project_button_icon_topic,
                R.drawable.project_button_icon_code,
                R.drawable.project_button_icon_member,
                R.drawable.project_button_icon_readme,
                R.drawable.project_button_icon_merge
        };

        final String[] titles = new String[]{
                "动态",
                "讨论",
                "代码",
                "成员",
                "Readme",
                "Pull Request"
        };

        for (int i = 0; i < buttons.length; ++i) {
            View item = root.findViewById(buttons[i]);
            item.findViewById(R.id.icon).setBackgroundResource(buttonBackgrounds[i]);
            ((TextView) item.findViewById(R.id.title)).setText(titles[i]);
            final int pos = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (buttons[pos]) {
                        case R.id.itemDynamic:
                            updateDynamic();
                            break;

                        case R.id.itemCode:
                            break;

                        case R.id.itemReadme:
                            break;

                        case R.id.itemMerge:
                            markUsed(RedPointTip.Type.Merge320);
                            break;
                    }

                    ProjectActivity_.intent(PublicProjectHomeFragment.this)
                            .mProjectObject(mProjectObject)
                            .mJumpType(ProjectActivity.PUBLIC_JUMP_TYPES[pos])
                            .start();
                }
            });

            if (titles[i].equals("动态")) {
                dynamicBadge = (BadgeView) item.findViewById(R.id.badge);
                Global.setBadgeView(dynamicBadge, mProjectObject.un_read_activities_count);
            } else {
                Global.setBadgeView((BadgeView) item.findViewById(R.id.badge), 0);
            }
        }

        updateRedPoinitStyle();
    }

    void updateRedPoinitStyle() {
        final int[] buttons = new int[]{
                R.id.itemMerge
        };

        final RedPointTip.Type[] types = new RedPointTip.Type[]{
                RedPointTip.Type.Merge320
        };

        for (int i = 0; i < buttons.length; ++i) {
            setRedPointStyle(buttons[i], types[i]);
        }
    }

    protected void postNetwork(String url) {
        postNetwork(url, new RequestParams(), url);
    }

    public void getNetwork(String url) {
        getNetwork(url, url);
    }

    @Click
    protected final void buttonStar(View v) {
        mButtonStar.changeState();
        if (mButtonStar.isChecked()) {
            postNetwork(mUrlStar);
        } else {
            postNetwork(mUrlUnstar);
        }
    }

    @Click
    protected final void buttonWatch(View v) {
        mButtonWatch.changeState();
        if (mButtonWatch.isChecked()) {
            postNetwork(mUrlWatch);
        } else {
            postNetwork(mUrlUnwatch);
        }
    }

    @Click
    protected final void buttonFork(View v) {
        if (mProjectObject.isMy()) {
            showButtomToast("不能fork自己的项目");
        } else {
            forkUrl = Global.HOST_API + mProjectObject.backend_project_path + "/git/fork";
            showDialog("fork", "fork将会将此项目复制到您的个人空间，确定要fork吗?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    postNetwork(forkUrl);
                    showProgressBar(true, "正在fork项目");
                }
            });
        }
    }

    class ProjectMarkButton {

        // 普通和选中
        String[] title;
        int[] titleColor;
        int[] icon;

        boolean mCheck = false;
        int mCount = 0;
        View mButton;

        ProjectMarkButton(View button, String[] title, int[] titleColor, int[] icon, final boolean mCheck, int mCount) {
            this.title = title;
            this.titleColor = titleColor;
            this.icon = icon;
            this.mCheck = mCheck;
            this.mCount = mCount;
            this.mButton = button;

            updateControl();
        }

        public void changeState() {
            checkButton(!mCheck);
        }

        public void checkButton(boolean check) {
            if (mCheck == check) {
                return;
            }

            mCheck = check;
            if (mCheck) {
                ++mCount;
            } else {
                --mCount;
            }

            updateControl();
        }

        private void updateControl() {
            mButton.setTag(mCheck);
            ((TextView) mButton.findViewById(R.id.count)).setText(String.valueOf(mCount));

            mButton.findViewById(R.id.icon).setBackgroundResource(!mCheck ? icon[0] : icon[1]);
            TextView title = (TextView) mButton.findViewById(R.id.title);
            title.setText(!mCheck ? this.title[0] : this.title[1]);
            title.setTextColor(!mCheck ? titleColor[0] : titleColor[1]);
        }

        public boolean isChecked() {
            return mCheck;
        }
    }
}
