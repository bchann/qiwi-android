package com.example.brianchan.qiwi_android;

import android.app.DownloadManager;
import android.content.Intent;

/**
 * Created by rishi on 2/5/17.
 */

public class RequestPresenter {
    RequestActivity activity;

    public RequestPresenter(RequestActivity activity) {
        this.activity = activity;
    }


    public void switchTabs() {
        Intent intent = new Intent(activity, QueueActivity.class);
        activity.startActivity(intent);
    }

    public void acceptRequest() {

    }


    public void rejectRequest() {

    }
}
