package com.example.administrator.technologystackapp.activities.bean;

import android.view.View;

public class TextAndListener {
    private String text;
    private View.OnClickListener listener;

    public TextAndListener(String text, View.OnClickListener listener) {
        this.text = text;
        this.listener = listener;
    }

    public String getText() {
        return text;
    }

    public View.OnClickListener getListener() {
        return listener;
    }
}
