package com.nicolas.coding.task;

import android.content.Context;
import android.widget.LinearLayout;

import com.nicolas.coding.R;

import org.androidannotations.annotations.EViewGroup;

/**
 * Created by chaochen on 15/1/4.
 */
@EViewGroup(R.layout.activity_task_add_head)
public class TaskHead extends LinearLayout {

    public TaskHead(Context context) {
        super(context);
    }

}
