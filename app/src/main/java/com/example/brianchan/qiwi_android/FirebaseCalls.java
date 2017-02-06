package com.example.brianchan.qiwi_android;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Brian Chan on 2/5/2017.
 */

public class FirebaseCalls {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference roomRef = database.getReference("Rooms");
    private String roomid;
    private String request = "RequestList";
    private String play = "Playlist";
    private String hist = "History";
    private String playSongs, requestSongs, histSongs;

    //Assigns the roomid
    public FirebaseCalls(String roomid) {
        this.roomid = roomid;

        roomRef = roomRef.child(roomid);

        ValueEventListener playListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playSongs = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        roomRef.child(play).addValueEventListener(playListener);

        ValueEventListener requestListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requestSongs = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        roomRef.child(request).addValueEventListener(requestListener);

        ValueEventListener histListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                histSongs = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        roomRef.child(hist).addValueEventListener(histListener);
    }

    //Plays the song, returns the song to be played or "" if the playlist is empty
    public String playSong() {
        String song = pop(play);

        if (song.equals("")) {
            return song;
        }

        push(hist, song);

        return song;
    }

    //Add asong to the end of the request list
    public boolean addRequest(String song) {
        return push(request, song);
    }

    //Approves a song request and pushes it onto the playlist
    public boolean approveRequest() {
        String song = pop(request);

        return !song.equals("") && push(play, song);

    }

    //Rejects a song request by removing it from the request list
    public boolean rejectRequest() {
        pop(request);

        return true;
    }

    //Removes and returns the first song in the song string. Returns "" if empty
    //origin: which list "requests, play, history"
    private String pop(String origin) {
        String songs = fetchSong(origin);
        String song = "";

        while (songs.length() >= 0) {
            if (songs.charAt(0) == '/') {
                songs = songs.substring(1);
                roomRef.child(origin).child("songs").setValue(songs);
                break;
            }

            song = song + songs.charAt(0);
            songs = songs.substring(1);
        }

        return song;
    }

    //Pushes a song to the end of the song string
    private boolean push(String dest, String song) {
        String songs = fetchSong(dest);

        songs = songs + song + '/';

        roomRef.child(dest).child("songs").setValue(songs);

        return true;
    }

    //Removes a song from a position in the song string
    private boolean remove(String origin, int pos) {
        //TODO: implement this after mvp

        return true;
    }

    private String fetchSong(String origin) {
        if (origin.equals(request)) {
            return requestSongs;
        }
        else if (origin.equals(play)){
            return playSongs;
        }
        else if (origin.equals(hist)) {
            return histSongs;
        }

        return "";
    }
}
