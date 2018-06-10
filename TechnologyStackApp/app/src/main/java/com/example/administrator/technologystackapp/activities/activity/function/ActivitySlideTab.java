package com.example.administrator.technologystackapp.activities.activity.function;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;
import com.example.administrator.technologystackapp.activities.custom.fragment.Fragment1;
import com.example.administrator.technologystackapp.activities.custom.fragment.Fragment2;
import com.example.administrator.technologystackapp.activities.custom.fragment.Fragment3;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 2018年6月8日 今晚的任务就是，把这个，两层滑动，外面增加一层头部的滑动，
 * 里面RecyclerView里面增加一层下拉和上拉滑动
 */
public class ActivitySlideTab extends BaseActivity {

    @BindView(R.id.ll_foot)
    LinearLayout ll_main;
    @BindView(R.id.analyst_tabs)
    TabLayout tlTabs;
    @BindView(R.id.analyst_pages)
    ViewPager vpPages;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_tab);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        vpPages.setAdapter(new AnalystAdapter(getSupportFragmentManager()));
        tlTabs.setupWithViewPager(vpPages);
    }


    /**
     * TabLayout+ViewPaper+Fragment，实现可滑动的多tab布局
     */
    private class AnalystAdapter extends FragmentPagerAdapter {

        AnalystAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("AnalystAdapter", "" + position);// 每一个position位置上的getItem最多只会被调用一次
            switch (position) {
                case 0:
                    if (fragment1 == null) {
                        fragment1 = new Fragment1();
                    }
                    return fragment1;
                case 1:
                    if (fragment2 == null) {
                        fragment2 = new Fragment2();
                    }
                    return fragment2;
                case 2:
                    if (fragment3 == null) {
                        fragment3 = new Fragment3();
                    }
                    return fragment3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "第一页";
                case 1:
                    return "第二页";
                case 2:
                    return "第三页";
                default:
                    return "";
            }
        }

    }
}
