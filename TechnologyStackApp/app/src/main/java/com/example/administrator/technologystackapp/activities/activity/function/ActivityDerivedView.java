package com.example.administrator.technologystackapp.activities.activity.function;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;

/**
 * 派生view
 */
public class ActivityDerivedView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_derived_view);
    }

}
