package com.nicolas.coding.project.detail.merge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicolas.coding.R;
import com.nicolas.coding.common.widget.DataAdapter;
import com.nicolas.coding.model.DiffFile;

/**
 * Created by chenchao on 15/6/2.
 * 应该为 DataAdapter<DiffFile.DiffSingleFile>，
 * 但为了方便 CommitFileAdapter 继承，CommitFileAdapter 有2种数据类型，所以用了 Object，
 */
public class MergeFileAdapter extends DataAdapter<Object> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder holder;
        if (convertView == null) {
            holder = new ViewHoder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mergefile_list_item, parent, false);
            holder.icon = convertView.findViewById(R.id.icon);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.insertion = (TextView) convertView.findViewById(R.id.insertion);
            holder.deletion = (TextView) convertView.findViewById(R.id.deletion);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoder) convertView.getTag();
        }

        DiffFile.DiffSingleFile data = (DiffFile.DiffSingleFile) getItem(position);
        holder.icon.setBackgroundResource(data.getIconId());
        holder.title.setText(data.getName());
        holder.insertion.setText(data.getInsertions());
        holder.deletion.setText(data.getDeletions());

        return convertView;
    }

    private static class ViewHoder {
        View icon;
        TextView title;
        TextView insertion;
        TextView deletion;
    }
}
