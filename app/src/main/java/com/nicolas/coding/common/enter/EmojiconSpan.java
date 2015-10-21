package com.nicolas.coding.common.enter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

import com.nicolas.coding.common.MyImageGetter;
import com.nicolas.coding.message.EmojiFragment;

/**
 * Created by chaochen on 14-11-12.
 */
class EmojiconSpan extends DynamicDrawableSpan {
    private final Context mContext;
    private final int mResourceId;
    private Drawable mDrawable;
    private boolean mIsMonkey;

    public EmojiconSpan(Context context, String iconName) {
        super();
        mContext = context;

        String name = EmojiFragment.textToMonkdyMap.get(iconName);
        if (name == null) {
            name = iconName;
            mIsMonkey = false;
        } else {
            mIsMonkey = true;
        }

        mResourceId = MyImageGetter.getResourceId(name);
    }

    @Override
    public Drawable getDrawable() {
        if (mDrawable == null) {
            try {
                mDrawable = mContext.getResources().getDrawable(mResourceId);
                DrawableTool.zoomDrwable(mDrawable, mIsMonkey);
            } catch (Exception e) {
            }
        }
        return mDrawable;
    }
}