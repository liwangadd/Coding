package com.nicolas.coding.maopao.item;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.nicolas.coding.R;
import com.nicolas.coding.common.DialogCopy;
import com.nicolas.coding.common.Global;
import com.nicolas.coding.common.HtmlContent;
import com.nicolas.coding.common.LongClickLinkMovementMethod;
import com.nicolas.coding.maopao.MaopaoListFragment;
import com.nicolas.coding.model.Maopao;

/**
 * Created by chaochen on 15/1/14.
 */
class CommentItem {

    private TextView comment;
    private TextView name;
    private TextView time;
    private View layout;

    public CommentItem(View convertView, View.OnClickListener onClickComment, int i) {
        layout = convertView;
        layout.setOnClickListener(onClickComment);
        name = (TextView) convertView.findViewById(R.id.name);
        time = (TextView) convertView.findViewById(R.id.time);
        comment = (TextView) convertView.findViewById(R.id.comment);
        comment.setMovementMethod(LongClickLinkMovementMethod.getInstance());
        comment.setOnClickListener(onClickComment);
        comment.setOnLongClickListener(DialogCopy.getInstance());
    }

    public void setContent(Maopao.Comment commentData, Html.ImageGetter imageGetter, Html.TagHandler tagHandler) {
        layout.setTag(MaopaoListFragment.TAG_COMMENT, commentData);
        comment.setTag(MaopaoListFragment.TAG_COMMENT, commentData);
        comment.setTag(MaopaoListFragment.TAG_COMMENT_TEXT, commentData.content);

        name.setText(commentData.owner.name);
        time.setText(Global.dayToNow(commentData.created_at));
        Global.MessageParse parse = HtmlContent.parseMessage(commentData.content);
        comment.setText(Global.changeHyperlinkColor(parse.text, imageGetter, tagHandler));
    }

    public void setVisibility(int visibility) {
        layout.setVisibility(visibility);
    }
}
