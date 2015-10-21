package com.nicolas.coding.subject;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nicolas.coding.R;
import com.nicolas.coding.common.Global;
import com.nicolas.coding.common.MyImageGetter;
import com.nicolas.coding.common.network.BaseFragment;
import com.nicolas.coding.maopao.MaopaoDetailActivity_;
import com.nicolas.coding.model.Maopao;
import com.nicolas.coding.subject.adapter.SubjectSearchListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 15-7-21.
 * <p/>
 * 搜索冒泡的数据
 */
@EFragment(R.layout.subject_search_fragment)
public class SubjectSearchFragment extends BaseFragment {

    @ViewById
    ListView listView;
    SubjectSearchListAdapter mSubjectSearchListAdapter;

    private String searchUrl = Global.HOST_API + "/search/quick?q=";
    private String searchTag = "search_tag";


    private TextView mSearchResultView;

    private MyImageGetter myImageGetter;


    // 当前的搜索条件
    private String mCondition = "";
    // 热门话题列表的数据
    private List<Maopao.MaopaoObject> maopaoObjectList = new ArrayList<>();
    private AdapterView.OnItemClickListener mSubjectItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int pos = position - listView.getHeaderViewsCount();
            if (pos >= 0 && pos < maopaoObjectList.size()) {
                MaopaoDetailActivity_.intent(getActivity()).mMaopaoObject(maopaoObjectList.get(pos)).start();
            }
        }
    };

    public void updateData(String condition) {
        if (!mCondition.equals(condition)) {
            mCondition = condition;
            searchMaopao();
        }
    }

    @AfterViews
    void init() {
        myImageGetter = new MyImageGetter(getActivity());
        mSubjectSearchListAdapter = new SubjectSearchListAdapter(getActivity(), maopaoObjectList, myImageGetter);
        initSearchHeaderView();
        listView.setAdapter(mSubjectSearchListAdapter);
        listView.setOnItemClickListener(mSubjectItemClickListener);
        notifyDataSetChange();

    }

    void initSearchHeaderView() {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.subject_search_list_header, null);
        mSearchResultView = (TextView) headerView.findViewById(R.id.maopao_search_result);
        listView.addHeaderView(headerView);

    }

    private void searchMaopao() {
        if (!TextUtils.isEmpty(mCondition)) {
            showDialogLoading();
            getNetwork(searchUrl + Global.encodeUtf8(mCondition), searchTag);
        }
    }

    private void showResultView() {
        mSearchResultView.setText(String.format("共搜索到 %s 个与\"%s\"相关的冒泡", maopaoObjectList.size(), mCondition));
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {
        if (searchTag.equals(tag)) {
            hideProgressDialog();
            maopaoObjectList.clear();
            JSONArray jsonArray = respanse.optJSONObject("data").optJSONObject("tweets").optJSONArray("list");
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject json = jsonArray.getJSONObject(i);
                Maopao.MaopaoObject maopaoObject = new Maopao.MaopaoObject(json);
                maopaoObjectList.add(maopaoObject);
            }
            notifyDataSetChange();
        }
    }

    private void notifyDataSetChange() {
        if (mSubjectSearchListAdapter != null) {
            mSubjectSearchListAdapter.notifyDataSetChanged();
            showResultView();
        }
    }

}
