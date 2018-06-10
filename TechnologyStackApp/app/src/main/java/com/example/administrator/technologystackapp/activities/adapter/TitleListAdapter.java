package com.example.administrator.technologystackapp.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.bean.TextAndListener;

import java.util.List;

public class TitleListAdapter extends BaseAdapter {

    private Context mContext;
    private List<TextAndListener> listData;

    public TitleListAdapter(Context context, List<TextAndListener> listData) {
        this.listData = listData;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (listData == null) return 0;
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        if (listData == null) return null;
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_adapter, null);
            holder = new ViewHolder();
            holder.tv_title = convertView.findViewById(R.id.tv_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_title.setText(listData.get(position).getText());
        holder.tv_title.setOnClickListener(listData.get(position).getListener());

        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
    }
}
