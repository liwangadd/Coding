package com.nicolas.coding.project.detail.merge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import com.nicolas.coding.R;
import com.nicolas.coding.common.comment.BaseCommentParam;
import com.nicolas.coding.model.BaseComment;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by chenchao on 15/5/28.
 */
public class MergeCommentAdaper extends SimpleData1Adaper<BaseComment> implements StickyListHeadersAdapter,
        SectionIndexer {
    public MergeCommentAdaper(BaseCommentParam param) {
        super(param);
    }

    @Override
    public Object[] getSections() {
        return new String[]{};
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.divide_15_top_bottom,
                    viewGroup, false);
        }

        return view;
    }

    @Override
    public long getHeaderId(int i) {
        return 0;
    }
}
