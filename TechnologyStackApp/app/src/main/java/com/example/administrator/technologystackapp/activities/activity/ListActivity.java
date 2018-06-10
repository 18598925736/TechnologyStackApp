package com.example.administrator.technologystackapp.activities.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityAnimatorMenu;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityCustomDanMuLayout;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityCustomLayout;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityCustomSurfaceView;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityCustomView;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityDerivedView;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityHorizontalMenu;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityMovingTextView;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityRecyclerView;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityRoundedImage;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityScrollConflict;
import com.example.administrator.technologystackapp.activities.activity.function.ActivityScrollListView;
import com.example.administrator.technologystackapp.activities.activity.function.ActivitySlideTab;
import com.example.administrator.technologystackapp.activities.activity.function.ActivitySwipe2delete;
import com.example.administrator.technologystackapp.activities.adapter.TitleListAdapter;
import com.example.administrator.technologystackapp.activities.bean.TextAndListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 把这个作为List
 */
public class ListActivity extends Activity {

    private Context mContext;

    /**
     * 如果这个activity 在仓促被关闭之后  又被重新初始化，那么这个参数Bundle 将会包含activity的数据.
     * 否则，Bundle将会是空
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     **/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list);// 这里只是发了一个消息给UI线程，你要绘制这个layout，并不是说这一行执行完了，绘制就一定结束了
        mContext = this;
        initData();
        initView();
    }

    private ListView lv_title;
    private List<TextAndListener> list_data;
    private ImageView iv_header;

    private Intent mIntent;

    private void initData() {
        list_data = new ArrayList<>();
        list_data.add(new TextAndListener("自定义控件-原生控件派生类", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityDerivedView.class);
            }
        }));
        list_data.add(new TextAndListener("自定义控件-继承View", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityCustomView.class);
            }
        }));
        list_data.add(new TextAndListener("自定义控件-继承SurfaceView", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityCustomSurfaceView.class);
            }
        }));
        list_data.add(new TextAndListener("自定义控件-继承ViewGroup", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityCustomLayout.class);
            }
        }));
        list_data.add(new TextAndListener("圆形头像", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityRoundedImage.class);
            }
        }));
        list_data.add(new TextAndListener("自定义弹幕布局", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityCustomDanMuLayout.class);
            }
        }));
        list_data.add(new TextAndListener("自定义动态菜单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityAnimatorMenu.class);
            }
        }));

        list_data.add(new TextAndListener("多层嵌套可滑动ScrollView", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityScrollConflict.class);
            }
        }));

        list_data.add(new TextAndListener("多层嵌套可滑动listView", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityScrollListView.class);
            }
        }));

        list_data.add(new TextAndListener("横向滑动的TabViewGroup", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityHorizontalMenu.class);
            }
        }));

        list_data.add(new TextAndListener("TextView走马灯", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityMovingTextView.class);
            }
        }));

        list_data.add(new TextAndListener("带CheckBox的RecyclerView实例", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivityRecyclerView.class);
            }
        }));
        list_data.add(new TextAndListener("可滑动可切换布局-TabLayout+ViewPaper+RecyclerView", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivitySlideTab.class);
            }
        }));
        list_data.add(new TextAndListener("左滑布局", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(ActivitySwipe2delete.class);
            }
        }));

    }


    private void initView() {
        lv_title = findViewById(R.id.lv_title);

        TitleListAdapter titleListAdapter = new TitleListAdapter(mContext, list_data);
        lv_title.setAdapter(titleListAdapter);
    }

    private void jumpTo(Class activityClass) {
        mIntent = new Intent(mContext, activityClass);
        startActivity(mIntent);
    }

}
