package com.example.brianchan.qiwi_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QueueActivity extends AppCompatActivity {
    QueuePresenter presenter;

    TextView currentSongText;
    Button requestsBtn;
    Button togglePlayBtn;
    ListView playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        presenter = new QueuePresenter(this);

        currentSongText = (TextView) findViewById(R.id.currentSongText);
        requestsBtn = (Button) findViewById(R.id.requestsBtn);
        togglePlayBtn = (Button) findViewById(R.id.togglePlayBtn);
        playlist = (ListView) findViewById(R.id.playlist);
    }

    // onclick method for requestsBtn
    public void switchTabs(View v) {
        presenter.switchTabs();
    }

    // onclick method for rejectBtn
    public void togglePlayPause(View v) {
        presenter.togglePlayPause();
    }
}
