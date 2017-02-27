package com.example.brianchan.qiwi_android;

import android.content.Intent;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Connectivity;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Metadata;
import com.spotify.sdk.android.player.PlaybackBitrate;
import com.spotify.sdk.android.player.PlaybackState;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.ArrayList;

/**
 * Created by rishi on 2/5/17.
 */

public class QueuePresenter {
    QueueActivity activity;
    MainActivity main;
    private String roomid = "room1";
    //private FirebaseCalls queueCalls = new FirebaseCalls(roomid);
    private boolean isPaused = false;
    Player.OperationCallback op = new Player.OperationCallback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(Error error) {

        }
    };

    public QueuePresenter(QueueActivity activity) {
        this.activity = activity;
    }

    public void switchTabs() {
        Intent intent = new Intent(activity, RequestActivity.class);
        activity.startActivity(intent);
    }

    public void togglePlayPause() {
        Player player = main.mPlayer;
        if (player.getPlaybackState().isPlaying) {
            if(isPaused == true){
                player.resume(op);
                isPaused = false;
            }
            else {
                player.pause(op);
                isPaused = true;
            }
        }
        else{
            /*ArrayList<String> playList = queueCalls.fetchSongList("Playlist");
            player.playUri(op, playList.get(0),0, 0);
            isPaused = false;
            queueCalls.playSong();*/
        }

    }
}
