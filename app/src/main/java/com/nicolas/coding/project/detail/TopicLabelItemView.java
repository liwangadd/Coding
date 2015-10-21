package com.nicolas.coding.project.detail;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nicolas.coding.R;
import com.nicolas.coding.common.widget.LabelTextView;
import com.nicolas.coding.model.TopicLabelObject;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Neutra on 2015/4/25.
 *
 */
@EViewGroup(R.layout.activity_topic_label_item)
public class TopicLabelItemView extends RelativeLayout implements Checkable {
    @ViewById
    LabelTextView textView;
    @ViewById
    ImageView icon;

    TopicLabelObject data;
    private boolean checked = false;

    public TopicLabelItemView(Context context) {
        super(context);
    }

    public TopicLabelItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopicLabelItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TopicLabelItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        icon.setImageResource(checked ? R.drawable.ic_topic_label_checked : R.drawable.ic_topic_label_unchecked);
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }

    public void bind(TopicLabelObject data) {
        this.data = data;
        textView.setText(data.name, data.getColor());
    }
}
