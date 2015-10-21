package com.nicolas.coding.common.comment;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.nicolas.coding.R;
import com.nicolas.coding.common.DialogCopy;
import com.nicolas.coding.common.Global;
import com.nicolas.coding.common.HtmlContent;
import com.nicolas.coding.common.ImageLoadTool;
import com.nicolas.coding.common.LongClickLinkMovementMethod;
import com.nicolas.coding.maopao.MaopaoListFragment;
import com.nicolas.coding.model.BaseComment;

/**
 * Created by chaochen on 14-10-27.
 */
public class HtmlCommentHolder extends BaseCommentHolder {

    protected TextView content;

    public HtmlCommentHolder(View convertView, View.OnClickListener onClickComment, Html.ImageGetter imageGetter, ImageLoadTool imageLoadTool, View.OnClickListener clickUser) {
        super(convertView, onClickComment, imageGetter, imageLoadTool, clickUser);

        content = (TextView) convertView.findViewById(R.id.content);
        content.setMovementMethod(LongClickLinkMovementMethod.getInstance());
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.performClick();
            }
        });

        content.setOnLongClickListener(DialogCopy.getInstance());
    }

    public void setContent(BaseComment comment) {
        super.setContent(comment);

        String contentString = comment.content;
        Global.MessageParse parse = HtmlContent.parseMessage(contentString);
        content.setText(Global.changeHyperlinkColor(parse.text, imageGetter, Global.tagHandler));
        content.setTag(comment);
        content.setTag(MaopaoListFragment.TAG_COMMENT_TEXT, parse.text);
    }

}
