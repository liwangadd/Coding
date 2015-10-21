package com.nicolas.coding.project.detail;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.nicolas.coding.ImagePagerFragment;
import com.nicolas.coding.R;
import com.nicolas.coding.common.BlankViewDisplay;
import com.nicolas.coding.common.FileUtil;
import com.nicolas.coding.common.Global;
import com.nicolas.coding.model.AttachmentFileObject;
import com.nicolas.coding.project.detail.file.FileDynamicActivity;
import com.nicolas.coding.project.detail.file.TxtEditActivity;
import com.nicolas.coding.project.detail.file.TxtEditActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 展示某一项目文档目录下面TXT文件的Activity
 * Created by yangzhen
 */
@EActivity(R.layout.activity_attachments_text)
public class AttachmentsTextDetailActivity extends AttachmentsDetailBaseActivity {

    private static final int RESULT_MODIFY_TXT = 1;
    @ViewById
    TextView textView;
    @ViewById
    View blankLayout;
    boolean downloadFileSuccess = false;
    String urlFiles = Global.HOST_API + "/project/%s/files/%s/view";
    AttachmentFileObject mFiles = new AttachmentFileObject();

    @AfterViews
    protected final void initAttachmentsTextDetailActivity() {
        urlFiles = String.format(urlFiles, mProjectObjectId, mAttachmentFileObject.file_id);
        if (mFile.exists()) {
            textView.setText(TxtEditActivity.readPhoneNumber(mFile));
        } else {
            showDialogLoading();
            getFileUrlFromNetwork();
        }
    }

    private void updateLoadFile() {
        mFile = FileUtil.getDestinationInExternalPublicDir(getFileDownloadPath(), mAttachmentFileObject.getSaveName(mProjectObjectId));
        if (mFile.exists()) {
            textView.setText(TxtEditActivity.readPhoneNumber(mFile));
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.project_attachment_image, menu);
//        if (!mAttachmentFileObject.isOwner()) {
//            menu.findItem(R.id.action_delete).setVisible(false);
//        }
//
//        return super.onCreateOptionsMenu(menu);
//    }

    private void getFileUrlFromNetwork() {
        getNetwork(urlFiles, urlFiles);
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.project_attachment_txt;
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {
        super.parseJson(code, respanse, tag, pos, data);
        if (tag.equals(urlFiles)) {
            if (code == 0) {
                hideProgressDialog();
                JSONObject file = respanse.getJSONObject("data").getJSONObject("file");
                mFiles = new AttachmentFileObject(file);
                String content = respanse.getJSONObject("data").optString("content");
                textView.setText(content);
                invalidateOptionsMenu();

            } else {
                if (code == ImagePagerFragment.HTTP_CODE_FILE_NOT_EXIST) {
                    BlankViewDisplay.setBlank(0, this, true, blankLayout, null);
                } else {
                    BlankViewDisplay.setBlank(0, this, false, blankLayout, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getFileUrlFromNetwork();
                        }
                    });
                }

                hideProgressDialog();
                showErrorMsg(code, respanse);
            }
        }
    }

    @OptionsItem
    public void action_add() {
    }

    @OptionsItem
    protected final void action_edit() {
        FileDynamicActivity.ProjectFileParam param = new FileDynamicActivity.ProjectFileParam(mAttachmentFileObject,
                mProject);
        TxtEditActivity_.intent(this)
                .mParam(param)
                .startForResult(RESULT_MODIFY_TXT);
    }

    @OnActivityResult(RESULT_MODIFY_TXT)
    protected void onResultModify(int result, Intent intent) {
        if (result == Activity.RESULT_OK) {
            setResult(result, intent);
            mAttachmentFileObject = (AttachmentFileObject) intent.getSerializableExtra(AttachmentFileObject.RESULT);
            updateLoadFile();
        }
    }
}
