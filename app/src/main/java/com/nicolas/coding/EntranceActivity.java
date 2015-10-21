package com.nicolas.coding;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicolas.coding.login.ResetPasswordActivity_;
import com.nicolas.coding.login.UserActiveActivity_;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import com.nicolas.coding.common.Global;
import com.nicolas.coding.common.ImageLoadTool;
import com.nicolas.coding.common.LoginBackground;
import com.nicolas.coding.common.UnreadNotify;
import com.nicolas.coding.common.WeakRefHander;
import com.nicolas.coding.common.guide.GuideActivity;
import com.nicolas.coding.login.ZhongQiuGuideActivity;
import com.nicolas.coding.model.AccountInfo;
import com.nicolas.coding.model.UserObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by cc191954 on 14-8-14.
 * 启动页面
 */
@EActivity(R.layout.entrance_image)
public class EntranceActivity extends BaseActivity implements Handler.Callback {

    static final String HOST_CURRENT = Global.HOST_API + "/current_user";
    private static final int HANDLER_MESSAGE_ANIMATION = 0;
    private static final int HANDLER_MESSAGE_NEXT_ACTIVITY = 1;
    @ViewById
    ImageView image;
    @ViewById
    TextView title;
    @ViewById
    View foreMask;
    @ViewById
    View logo;
    @AnimationRes
    Animation entrance;
    Uri background = null;
    boolean mNeedUpdateUser = false;
    WeakRefHander mWeakRefHandler;

    @AfterViews
    void init() {
        Uri uriData = getIntent().getData();
        if (uriData != null) {
            String path = uriData.getPath();
            switch (path) {
                case "/app/detect": {
                    String link = Global.decodeUtf8(uriData.getQueryParameter("link"));
                    Uri uriLink = Uri.parse(link);
                    String linkPath = uriLink.getPath();

                    switch (linkPath) {
                        case "/activate":
                            UserActiveActivity_.intent(this)
                                    .link(link)
                                    .start();
                            break;
                        case "/user/resetPassword":
                            ResetPasswordActivity_.intent(this)
                                    .link(link)
                                    .start();
                            break;
                        default:
                            WebActivity_.intent(this)
                                    .url(link)
                                    .start();
                            break;
                    }
                    break;
                }

                default: {
                    Intent mainIntent = new Intent(this, MainActivity_.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mainIntent.putExtra("mPushUrl", uriData);
                    startActivity(mainIntent);
                }
            }

            finish();
            return;
        }

        mWeakRefHandler = new WeakRefHander(this);

        settingBackground();

        entrance.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!mNeedUpdateUser) {
                    mWeakRefHandler.start(HANDLER_MESSAGE_NEXT_ACTIVITY, 500);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        if (MyApp.sUserObject.global_key.isEmpty() && AccountInfo.isLogin(this)) {
            getNetwork(HOST_CURRENT, HOST_CURRENT);
            mNeedUpdateUser = true;
        }

        mWeakRefHandler.start(HANDLER_MESSAGE_ANIMATION, 900);
    }

    private void settingBackground() {
        if (ZhongQiuGuideActivity.isZhongqiu()) {
            ImageSize imageSize = new ImageSize(MyApp.sWidthPix, MyApp.sHeightPix);
            image.setImageBitmap(getImageLoad().imageLoader.loadImageSync("drawable://" + R.drawable.zhongqiu_init_photo, imageSize));
            title.setText("中秋快乐 © Mango");
            return;
        }

        LoginBackground.PhotoItem photoItem = new LoginBackground(this).getPhoto();
        File file = photoItem.getCacheFile(this);
        getImageLoad().imageLoader.clearMemoryCache();
        if (file.exists()) {
            background = Uri.fromFile(file);
            image.setImageBitmap(getImageLoad().imageLoader.loadImageSync("file://" + file.getPath(), ImageLoadTool.enterOptions));
            title.setText(photoItem.getTitle());

            if (photoItem.isGuoguo()) {
                hideLogo();
            }
        } else {
            ImageSize imageSize = new ImageSize(MyApp.sWidthPix, MyApp.sHeightPix);
            image.setImageBitmap(getImageLoad().imageLoader.loadImageSync("drawable://" + R.drawable.entrance1, imageSize));
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == HANDLER_MESSAGE_ANIMATION) {
            playAnimator1();
        } else if (msg.what == HANDLER_MESSAGE_NEXT_ACTIVITY) {
            next();
        }
        return true;
    }

    private void playAnimator1() {
        foreMask.startAnimation(entrance);
    }

    private void hideLogo() {
//        mask.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {
        if (tag.equals(HOST_CURRENT)) {
            mNeedUpdateUser = false;
            if (code == 0) {
                UserObject user = new UserObject(respanse.getJSONObject("data"));
                AccountInfo.saveAccount(this, user);
                MyApp.sUserObject = user;
                AccountInfo.saveReloginInfo(this, user.email, user.global_key);
                next();
            } else {
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("更新")
                        .setMessage("刷新账户信息失败")
                        .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getNetwork(HOST_CURRENT, HOST_CURRENT);
                            }
                        })
                        .setNegativeButton("关闭程序", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
                dialogTitleLineColor(dialog);

            }
        }
    }

    void next() {
        Intent intent;
        String mGlobalKey = AccountInfo.loadAccount(this).global_key;
        if (mGlobalKey.isEmpty()) {
            intent = new Intent(this, GuideActivity.class);
            if (background != null) {
                intent.putExtra(LoginActivity.EXTRA_BACKGROUND, background);
            }

        } else {
//            if (AccountInfo.needDisplayGuide(this)) {
//                intent = new Intent(this, FeatureActivity_.class);
//            } else {
                intent = new Intent(this, MainActivity_.class);
//            }
        }

        startActivity(intent);
        overridePendingTransition(R.anim.scroll_in, R.anim.scroll_out);

        UnreadNotify.update(this);
        finish();
    }
}

