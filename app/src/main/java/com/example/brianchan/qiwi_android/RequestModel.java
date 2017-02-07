package com.example.brianchan.qiwi_android;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Brian Chan on 2/5/2017.
 */

class RequestModel {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference roomRef = database.getReference("Rooms");
    private String roomid = "room1"; //TODO: CHANGE, CONST FOR TESTING
    private static final String
            request = "RequestList",
            play = "Playlist",
            hist = "History";
    private String playSongs, requestSongs, histSongs;

    //Assigns the roomid
    RequestModel(String roomid) {
        this.roomid = roomid;

        roomRef = roomRef.child(roomid);

        ValueEventListener playListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playSongs = dataSnapshot.getValue(String.class);

                System.err.println(playSongs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        roomRef.child(play).child("songs").addValueEventListener(playListener);

        ValueEventListener requestListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requestSongs = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        roomRef.child(request).child("songs").addValueEventListener(requestListener);

        ValueEventListener histListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                histSongs = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        roomRef.child(hist).child("songs").addValueEventListener(histListener);
    }

    Request getCurrentRequest() {
        return new Request(peek(request), this);
    }

    //Plays the song, returns the song to be played or "" if the playlist is empty
    String playSong() {
        String song = pop(play);

        if (song.equals("")) {
            return song;
        }

        push(hist, song);

        return song;
    }

    //Add asong to the end of the request list
    boolean addRequest(String song) {
        return push(request, song);
    }

    //Approves a song request and pushes it onto the playlist
    boolean approveRequest() {
        String song = pop(request);

        return !song.equals("") && push(play, song);

    }

    //Rejects a song request by removing it from the request list
    boolean rejectRequest() {
        pop(request);

        return true;
    }

    public ArrayList<String> fetchSongList(String origin) {
        String songs = playSongs;
        String song = "";
        ArrayList<String> songsArr = new ArrayList<>();

        System.err.println("songs: " + songs);

        if (songs.equals("")) {
            return songsArr;
        }

        while (!songs.equals("")) {
            if (songs.charAt(0) == '/') {
                songs = songs.substring(1);
                songsArr.add(song);
                song = "";
            }

            song = song + songs.charAt(0);
            songs = songs.substring(1);
        }

        return songsArr;
    }

    //Removes and returns the first song in the song string. Returns "" if empty
    //origin: which list "requests, play, history"
    private String pop(String origin) {
        String songs = fetchSong(origin);
        String song = "";

        while (songs.length() > 0) {
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

    private String peek(String origin) {
        String songs = fetchSong(origin);
        String song = "";

        while (songs.length() > 0) {
            if (songs.charAt(0) == '/') {
                break;
            }

            song = song + songs.charAt(0);
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
        switch (origin) {
            case request:
                return requestSongs;
            case play:
                return playSongs;
            case hist:
                return histSongs;
        }

        return "";
    }
}

class Request {
    String songid;
    private RequestModel model;

    Request(String songid, RequestModel model) {
        this.songid = songid;
        this.model = model;
    }

    //Adds the first song from the request list to the playlist
    void addToQueue() {
        model.approveRequest();
    }

    //Deletes the first song from the request list
    void delete() {
        model.rejectRequest();
    }

    //Plays the first song in the playlist
    void playSong() {
        model.playSong();
    }

    //Add a request to the request list
    void addRequest() {
        model.addRequest(songid);
    }
}