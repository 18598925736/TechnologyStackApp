package com.example.administrator.technologystackapp.activities.custom.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.custom.MyRecyclerView;
import com.example.administrator.technologystackapp.activities.custom.adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    MyRecyclerView rv_data1;
    SwipeRefreshLayout swipe_fresh_layout;
    private List<String> data;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private int count = 0;
    private int moreIndex = 0;

    private boolean ifLazyLoad = false;
    private boolean ifLoading = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("Fragmenttest1", "onCreateView1:");
        View v = inflater.inflate(R.layout.fragment_1, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        rv_data1 = v.findViewById(R.id.rv_data1);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_data1.setLayoutManager(linearLayoutManager);
        rv_data1.setAdapter(myRecyclerViewAdapter);
        rv_data1.setItemAnimator(new DefaultItemAnimator());

        swipe_fresh_layout = v.findViewById(R.id.swipe_fresh_layout);
        swipe_fresh_layout.setColorSchemeColors(Color.RED, Color.BLUE, Color.BLACK, Color.YELLOW);//设置进度框颜色
        swipe_fresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 异步任务去加载 去影响adapter的内容
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                }, 1000);
            }
        });

        swipe_fresh_layout.setRefreshing(true);
        rv_data1.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) rv_data1.getLayoutManager();
                int itemCount = layoutManager.getItemCount();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if (!ifLoading & itemCount < (lastPosition + 2)) {
                    Log.d("onScrolled", "loadMoreData ready");
                    ifLoading = true;
                    if (myRecyclerViewAdapter.isHasMore())
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadMoreData();
                            }
                        }, 1000);
                }
            }
        });
    }

    private void loadData() {
        moreIndex = 0;
        ifLoading = false;
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("refreshCount:" + count + "--" + i);
        }
        myRecyclerViewAdapter.setData(data, true);
        swipe_fresh_layout.setRefreshing(false);
        count++;
    }

    private void loadMoreData() {
        ifLoading = false;
        List<String> moreData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            moreIndex++;
            moreData.add("moreData:" + moreIndex);
        }
        if (moreIndex <= 30)
            myRecyclerViewAdapter.addData(moreData, true);
        else
            myRecyclerViewAdapter.addData(moreData, false);
    }

    /**
     * 当他的可见状态发生变化时，比如说，从不可见变成可见，就会回调setUserVisibleHint(true);从可见变成不可见，则是false
     * <p>
     * 我不明白，为什么这个方法，居然比onCreateView还要先执行。所以为了防止意外，我要把延迟加载的动作增加延时时间。
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("Fragmenttest1", "Fragment1:" + isVisibleToUser);
        if (isVisibleToUser) {
            onLazyLoad();
            ifLazyLoad = true;
        }
    }

    private void onLazyLoad() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        }, 1000);
    }

    @Override
    public void onAttach(Context context) {
        Log.d("lifecycle", "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.d("lifecycle", "onDetach");
        ifLazyLoad = false;
        super.onDetach();
    }
}
