package com.example.administrator.technologystackapp.activities.activity.function;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.technologystackapp.R;
import com.example.administrator.technologystackapp.activities.activity.manager.BaseActivity;
import com.example.administrator.technologystackapp.activities.custom.Swipe2DeleteViewGroup;

public class ActivitySwipe2delete extends BaseActivity {

    private Swipe2DeleteViewGroup mSwipe2Delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_delete);
        mSwipe2Delete = (Swipe2DeleteViewGroup) findViewById(R.id.swipe2delete);
        mSwipe2Delete.setOnItemClickListener(new Swipe2DeleteViewGroup.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int index, boolean isCenterView) {
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    String str = textView.getText().toString();
                    Toast.makeText(ActivitySwipe2delete.this, String.format("%s , isCenterView: %s", str, isCenterView), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
