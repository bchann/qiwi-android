package com.example.brianchan.qiwi_android;

import android.content.Intent;

/**
 * Created by rishi on 2/5/17.
 */

public class QueuePresenter {
    QueueActivity activity;

    public QueuePresenter(QueueActivity activity) {
        this.activity = activity;
    }

    public void switchTabs() {
        Intent intent = new Intent(activity, RequestActivity.class);
        activity.startActivity(intent);
    }

    public void togglePlayPause() {}
}
