package com.example.brianchan.qiwi_android;

import android.app.DownloadManager;
import android.content.Intent;

/**
 * Created by rishi on 2/5/17.
 */

public class RequestPresenter {
    RequestActivity activity;
    RequestModel model;

    public RequestPresenter(RequestActivity activity) {
        this.activity = activity;
        //instantiate model

        inflateRequest(model.getCurrentRequest());
    }


    public void switchTabs() {
        Intent intent = new Intent(activity, QueueActivity.class);
        activity.startActivity(intent);
    }

    public void acceptRequest() {
        model.getCurrentRequest().addToQueue();
        inflateRequest(model.getCurrentRequest());
    }


    public void rejectRequest() {
        model.getCurrentRequest().delete();

    }

    public void inflateRequest(Request request) {
        activity.songNameText.setText(request.songTitle);
    }
}
