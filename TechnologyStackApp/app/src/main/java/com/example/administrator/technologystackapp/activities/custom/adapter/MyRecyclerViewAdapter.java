package com.example.administrator.technologystackapp.activities.custom.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.technologystackapp.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private List<String> data;

    private final static int ITEM_NORMAL = 1;
    private final static int ITEM_FOOT = 2;

    public boolean isHasMore() {
        return hasMore;
    }

    private boolean hasMore = true;

    public void setData(List<String> data, boolean hasMore) {
        this.hasMore = hasMore;
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<String> moreData, boolean hasMore) {
        this.hasMore = hasMore;
        data.addAll(moreData);
        notifyDataSetChanged();
    }

    public MyRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_NORMAL) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.layout_normal, parent, false));
        } else if (viewType == ITEM_FOOT) {
            Log.d("onCreateViewHolder", "找到foot");
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.layout_foot, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position < data.size())
            Log.d("onBindViewHolder", "0");
            if (holder instanceof MyHolder) {
                MyHolder myHolder = (MyHolder) holder;
                myHolder.tv_title.setText(data.get(position));
            } else if (holder instanceof FootHolder) {
                final FootHolder footHolder = (FootHolder) holder;
                if (hasMore) {
                    Log.d("onBindViewHolder", "1");
                    footHolder.tv_tips.setText("加载中...");
                    footHolder.progress_bar.setVisibility(View.VISIBLE);
                } else {
                    Log.d("onBindViewHolder", "2");
                    footHolder.tv_tips.setText("没有更多数据了");
                    footHolder.progress_bar.setVisibility(View.GONE);
                }
            }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : (data.size() + 1);//这里返回的是data size+1是因为，必须把最后一个位置预留给foot
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() - 1)//加入getItemCount是10，那实际上只有9个元素，因为最后一个是footer，这里，要把0-8设置为NORMAL的item，position为9设置为ITEM_foot;
            return ITEM_NORMAL;
        else {
            Log.d("getItemViewType", "找到foot");
            return ITEM_FOOT;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_title;

        public MyHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_foot;
        TextView tv_tips;
        ProgressBar progress_bar;

        public FootHolder(View itemView) {
            super(itemView);
            ll_foot = itemView.findViewById(R.id.ll_foot);
            tv_tips = itemView.findViewById(R.id.tv_tips);
            progress_bar = itemView.findViewById(R.id.progress_bar);

        }
    }
}
