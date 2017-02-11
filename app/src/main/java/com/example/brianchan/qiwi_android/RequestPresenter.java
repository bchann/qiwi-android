package com.example.brianchan.qiwi_android;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Handler;

/**
 * Created by rishi on 2/5/17.
 */

public class RequestPresenter {
    RequestActivity activity;
    RequestModel model;
    public Handler mHandler = new Handler();

    public RequestPresenter(RequestActivity activity) {
        this.activity = activity;
        //instantiate model
        model = new RequestModel("room1");
        //inflateRequest(model.getCurrentRequest());

        //Pause for 5 sec before running code
        mHandler.postDelayed(new Runnable() {
            public void run() {
                inflateRequest(model.getCurrentRequest());
            }
        }, 5000);
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
        inflateRequest(model.getCurrentRequest());
    }

    public void inflateRequest(Request request) {
        activity.songNameText.setText(request.songid);
    }
}
