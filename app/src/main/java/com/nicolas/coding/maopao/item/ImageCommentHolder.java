package com.nicolas.coding.maopao.item;

import android.text.Html;
import android.view.View;

import com.nicolas.coding.common.ImageLoadTool;
import com.nicolas.coding.common.comment.BaseCommentHolder;
import com.nicolas.coding.common.comment.BaseCommentParam;
import com.nicolas.coding.model.BaseComment;
import com.nicolas.coding.model.Commit;
import com.nicolas.coding.model.DynamicObject;

/**
 * Created by chenchao on 15/3/31.
 * 可以带多张小图片的评论item
 */
public class ImageCommentHolder extends BaseCommentHolder {

    private ContentAreaMuchImages contentArea;

    public ImageCommentHolder(View convertView, View.OnClickListener onClickComment, Html.ImageGetter imageGetter, ImageLoadTool imageLoadTool, View.OnClickListener clickUser, View.OnClickListener clickImage) {
        super(convertView, onClickComment, imageGetter, imageLoadTool, clickUser);
        contentArea = new ContentAreaMuchImages(convertView, onClickComment, clickImage, imageGetter, imageLoadTool); //
    }

    public ImageCommentHolder(View convertView, BaseCommentParam param) {
        super(convertView, param);
        this.contentArea = new ContentAreaMuchImages(convertView, param.onClickComment, param.mClickImage, param.imageGetter, param.imageLoadTool);
    }

    @Override
    public void setContent(Object data) {
        super.setContent(data);
        if (data instanceof BaseComment) {
            contentArea.setDataContent(((BaseComment) data).content, data);
        } else if (data instanceof Commit) {
            contentArea.setDataContent(((Commit) data).getTitle(), data);
        } else if (data instanceof DynamicObject.DynamicProjectFileComment) {
            contentArea.setDataContent(((DynamicObject.DynamicProjectFileComment) data).getComment(), data);
        }
    }
}
