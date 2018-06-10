package com.example.administrator.technologystackapp.activities.custom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.administrator.technologystackapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;

    //先造一组模拟数据
    private List<String> listData;

    private Map<Integer, Boolean> checkStatus;//用来记录所有checkbox的状态

    public MyAdapter(Context context) {
        this.context = context;
        initData();
    }

    private void initData() {
        listData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            listData.add("testData" + i);
        }

        checkStatus = new HashMap<>();
        for (int i = 0; i < listData.size(); i++) {
            checkStatus.put(i, false);// 默认所有的checkbox都是没选中
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_recycler_item, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, final int position) {
        holder.textView.setText(listData.get(position));
        holder.checkbox.setOnCheckedChangeListener(null);//清掉监听器
        holder.checkbox.setChecked(checkStatus.get(position));//设置选中状态
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {//再设置监听器
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkStatus.put(position, isChecked);//check状态一旦改变，保存的check值也要发生相应的变化
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listData == null) return 0;
        return listData.size();
    }
}