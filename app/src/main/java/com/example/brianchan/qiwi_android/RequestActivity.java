package com.example.brianchan.qiwi_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestActivity extends AppCompatActivity {

    RequestPresenter presenter;

    TextView songNameText;

    Button queueBtn;
    Button acceptBtn;
    Button rejectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        presenter = new RequestPresenter(this);

        songNameText = (TextView) findViewById(R.id.songNameText);
        queueBtn = (Button) findViewById(R.id.queueBtn);
        acceptBtn = (Button) findViewById(R.id.acceptBtn);
        rejectBtn = (Button) findViewById(R.id.rejectBtn);
    }

    // onclick method for queueBtn
    public void switchTabs(View v) {
        presenter.switchTabs();
    }

    // onclick method for acceptBtn
    public void acceptRequest(View v) {
        presenter.acceptRequest();
    }

    // onclick method for rejectBtn
    public void rejectRequest(View v) {
        presenter.rejectRequest();
    }
}
